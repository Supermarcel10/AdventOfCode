use std::fs;

fn get_highest_bank_joltage(bank: &str, size_of_search: usize) -> u64 {
    let mut bank: Vec<u32> = bank
        .chars()
        .map(|c| c.to_digit(10).expect("Failed to parse char {c}"))
        .collect();

    let mut highest_joltages = vec![0; size_of_search];
    for (i, highest_joltage) in highest_joltages.iter_mut().enumerate() {
        let mut idx = 0;
        let max = bank.len() + 1 - (size_of_search - i);

        for (i, v) in bank.iter().take(max).enumerate() {
            if *v > *highest_joltage {
                *highest_joltage = *v;
                idx = i;
            }
        }

        bank = bank[idx + 1..bank.len()].into();
    }

    highest_joltages
        .into_iter()
        .map(|d| char::from_digit(d, 10).unwrap())
        .collect::<String>()
        .parse::<u64>()
        .expect("Failed to parse end value")
}

fn main() {
    let file = fs::read_to_string("input.txt").expect("File not found!");
    let file = file.trim();

    let banks: Vec<&str> = file.split('\n').collect();

    let total_joltage: u64 = banks.iter().map(|&b| get_highest_bank_joltage(b, 2)).sum();
    println!("Part A: {total_joltage}");

    let total_joltage: u64 = banks.iter().map(|&b| get_highest_bank_joltage(b, 12)).sum();
    println!("Part B: {total_joltage}");
}
