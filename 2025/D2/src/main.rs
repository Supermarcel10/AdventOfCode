use std::fs;
use std::ops::RangeInclusive;

trait HyphenRangeDefinition {
    type Idx;

    fn parse_hyphen_range(&self) -> Option<RangeInclusive<Self::Idx>>;
}

impl HyphenRangeDefinition for &str {
    type Idx = u128;

    fn parse_hyphen_range(&self) -> Option<RangeInclusive<Self::Idx>> {
        let (min_str, max_str) = self.split_once('-').expect("Failed to split range");

        let min = min_str.parse::<Self::Idx>().ok()?;
        let max = max_str.parse::<Self::Idx>().ok()?;

        Some(min..=max)
    }
}

fn is_id_invalid_part_1(num: u128) -> bool {
    let num_str = num.to_string();
    let length = num_str.len();

    if !length.is_multiple_of(2) {
        return false;
    }

    let mid_point = length / 2;
    let left = &num_str[0..mid_point];
    let right = &num_str[mid_point..length];

    left == right
}

fn is_id_invalid_part_2(num: u128) -> bool {
    let num_str = num.to_string();
    let length = num_str.len();

    for number_of_splits in 2..=length {
        let length_of_each_split = length / number_of_splits;
        let first = &num_str[0..length_of_each_split];

        if num_str == first.repeat(number_of_splits) {
            return true;
        }
    }

    false
}

fn main() {
    let file = fs::read_to_string("input.txt").expect("File not found!");
    let file = file.trim();

    let ranges: Vec<_> = file
        .split(',')
        .filter_map(|s| s.parse_hyphen_range())
        .collect();

    let invalid_sum: u128 = ranges
        .clone()
        .into_iter()
        .flatten()
        .filter(|v| is_id_invalid_part_1(*v))
        .sum();

    println!("Part A: {invalid_sum}");

    let invalid_sum: u128 = ranges
        .into_iter()
        .flatten()
        .filter(|v| is_id_invalid_part_2(*v))
        .sum();

    println!("Part B: {invalid_sum}");
}
