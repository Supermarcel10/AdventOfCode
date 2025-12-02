use std::fs;



#[derive(Debug, Clone)]
enum Instruction {
    LEFT(u32),
    RIGHT(u32),
}

impl Instruction {
    fn parse(s: &str) -> Option<Self> {
        use crate::Instruction::*;

        if s.len() < 2 {
            return None;
        }

        let (direction, turns_str) = s.split_at(1);
        let turns = turns_str.parse::<u32>().ok()?;

        let instruction = match direction {
            "L" => LEFT(turns),
            "R" => RIGHT(turns),
            _ => return None,
        };

        Some(instruction)
    }

    fn get_new_dial(&self, original_dial: u32) -> u32 {
        use crate::Instruction::*;

        match self {
            LEFT(turns) => (original_dial as i64 - *turns as i64).rem_euclid(100) as u32,
            RIGHT(turns) => (original_dial as i64 + *turns as i64).rem_euclid(100) as u32,
        }
    }

    fn get_count_of_passes_through_zero(&self, original_dial: u32) -> u32 {
        use crate::Instruction::*;

        match self {
            LEFT(turns) => {
                if original_dial == 0 {
                    turns / 100
                } else {
                    (turns + (100 - original_dial)) / 100
                }
            },
            RIGHT(turns) => (turns + original_dial) / 100,
        }
    }
}



struct Safe {
    dial: u32,
    pub occurences_of_zero: u32,
    pub occurences_of_passing_zero: u32,
}

impl Safe {
    pub fn new() -> Self {
        Safe {
            dial: 50,
            occurences_of_zero: 0,
            occurences_of_passing_zero: 0,
        }
    }

    pub fn turn(&mut self, instruction: Instruction) {
        let passes_through_zero = instruction.get_count_of_passes_through_zero(self.dial);
        self.occurences_of_passing_zero += passes_through_zero;

        let new_dial = instruction.get_new_dial(self.dial);
        if new_dial == 0 {
            self.occurences_of_zero += 1;
        }

        // println!("Dial: {}\nInstruction: {instruction:?}\nPasses: {passes_through_zero}\nNew dial: {new_dial}\n\n", self.dial);
        self.dial = new_dial;
    }
}



fn main() {
    let file = fs::read_to_string("input.txt")
        .expect("File not found!");
    let lines: Vec<_> = file.lines().collect();

    let instructions: Vec<_> = lines
        .into_iter()
        .filter_map(|s| Instruction::parse(s))
        .collect();

    let mut safe = Safe::new();

    for instruction in instructions {
        safe.turn(instruction);
    }

    println!("Part A: {}", safe.occurences_of_zero);
    println!("Part B: {}", safe.occurences_of_passing_zero);
}

