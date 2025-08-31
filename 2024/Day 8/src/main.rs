use std::fs;
use std::collections::{HashMap, HashSet};

#[derive(Debug)]
struct Antenna {
    frequency : char,
    x : u8,
    y : u8,
}

#[derive(Debug)]
struct Antinode {
    frequency : char,
    x : u8,
    y : u8,
}

fn print_grid(antennas: &Vec<Antenna>, antinodes: &Vec<Antinode>, max_x: u8, max_y: u8) {
    for y in 0..max_y {
        for x in 0..max_x {
            if let Some(antenna) = antennas.iter().find(|a| a.x == x && a.y == y) {
                print!("{}", antenna.frequency);
            } else if antinodes.iter().any(|an| an.x == x && an.y == y) {
                print!("#");
            } else {
                print!(".");
            }
        }
        println!();
    }
}

fn parse_antennas(lines: &Vec<&str>) -> Vec<Antenna> {
    let mut antennas : Vec<Antenna> = vec![];

    for (y, line) in lines.iter().enumerate() {
        for (x, char) in line.chars().enumerate() {
            if char != '.' {
                antennas.push(
                    Antenna {
                        frequency: char,
                        x: x as u8,
                        y: y as u8
                    }
                );
            }
        }
    }

    antennas
}

fn group_antenna_by_frequency(antennas: &Vec<Antenna>) -> HashMap<char, Vec<&Antenna>> {
    let mut freq_groups: HashMap<char, Vec<&Antenna>> = HashMap::new();
    for antenna in antennas {
        freq_groups.entry(antenna.frequency)
            .or_insert(Vec::new())
            .push(antenna);
    }

    freq_groups
}

fn is_valid_position(x: u8, y: u8, max_x: u8, max_y: u8) -> bool {
    x > 0 || x < max_x || y > 0 || y < max_y
}

fn antinode_already_exists(antinode: &Antinode, antinodes: &Vec<Antinode>) -> bool {
    antinodes.iter().any(|an|
        an.frequency == antinode.frequency
            && an.x == antinode.x
            && an.y == antinode.y
    )
}

fn generate_antinodes(antennas: &Vec<Antenna>, max_x: u8, max_y: u8) -> Vec<Antinode> {
    let mut antinodes = Vec::new();
    let grouped_antennas= group_antenna_by_frequency(antennas);

    for (freq, group) in grouped_antennas {
        for i in 0..group.len() {
            for j in i + 1..group.len() {
                let a1 = group[i];
                let a2 = group[j];

                let dx = if a2.x >= a1.x { a2.x - a1.x } else { a1.x - a2.x };
                let dy = if a2.y >= a1.y { a2.y - a1.y } else { a1.y - a2.y };

                let positions: Vec<_> = [
                    a1.x.checked_sub(dx).and_then(|x| a1.y.checked_sub(dy).map(|y| (x, y))),
                    a2.x.checked_add(dx).and_then(|x| a2.y.checked_add(dy).map(|y| (x, y))),
                ].into_iter().flatten().collect();

                for (x, y) in positions.iter() {
                    let new_antinode = Antinode {
                        frequency: freq,
                        x: *x,
                        y: *y
                    };

                    if is_valid_position(*x, *y, max_x, max_y)
                        && !antinode_already_exists(&new_antinode, &antinodes) {
                        antinodes.push(new_antinode);
                    }
                }
            }
        }
    }

    antinodes
}

fn calculate_unique_count_of_antinode_locations(antinodes: &Vec<Antinode>) -> u16 {
    let mut unique_locations = HashSet::new();

    for antinode in antinodes {
        println!("{:?}", antinode);
        let success = unique_locations.insert((antinode.x, antinode.y));
        println!("{}", success);
    }

    unique_locations.len() as u16
}

fn main() {
    let file = fs::read_to_string("input.txt")
        .expect("File not found!");
    let lines: Vec<_> = file.lines().collect();

    let max_x = lines.first().map_or(
        0,
        |first_line| first_line.chars().count() as u8
    );
    let max_y= lines.len() as u8;

    let antennas = parse_antennas(&lines);
    let antinodes : Vec<Antinode> = generate_antinodes(&antennas, max_x, max_y);

    print_grid(&antennas, &antinodes, max_x, max_y);
    print!("{}", calculate_unique_count_of_antinode_locations(&antinodes));
}
