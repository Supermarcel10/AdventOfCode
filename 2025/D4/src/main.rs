use std::fs;

fn reset_grid(grid: &mut [Vec<char>]) {
    for row in grid {
        for c in row {
            if *c == 'x' {
                *c = '.';
            }
        }
    }
}

fn remove_papers(grid: &mut [Vec<char>]) -> u32 {
    reset_grid(grid);

    let rows = grid.len();
    let cols = grid[0].len();

    let directions = [
        (-1, -1),
        (-1, 0),
        (-1, 1),
        (0, -1),
        (0, 1),
        (1, -1),
        (1, 0),
        (1, 1),
    ];

    let mut forklift_accessible = 0;

    for y in 0..rows {
        for x in 0..cols {
            let v = grid[y][x];

            if v == '@' {
                let mut neighbor_rolls = 0;

                for (dy, dx) in directions {
                    let y = y as isize + dy;
                    let x = x as isize + dx;

                    if y >= 0 && y < rows as isize && x >= 0 && x < cols as isize {
                        let neighbor = grid[y as usize][x as usize];
                        if neighbor == '@' || neighbor == 'x' {
                            neighbor_rolls += 1;
                        }
                    }
                }

                if neighbor_rolls < 4 {
                    forklift_accessible += 1;

                    grid[y][x] = 'x';
                }
            }
        }
    }

    forklift_accessible
}

fn main() {
    let file = fs::read_to_string("input.txt").expect("File not found!");
    let file = file.trim();

    let mut grid: Vec<Vec<char>> = file.split('\n').map(|row| row.chars().collect()).collect();

    let mut removed_papers = remove_papers(&mut grid);
    let mut total_removed_papers = removed_papers;
    println!("Part A: {removed_papers}");

    while removed_papers != 0 {
        removed_papers = remove_papers(&mut grid);
        total_removed_papers += removed_papers;
    }
    println!("Part B: {total_removed_papers}");

    for row in grid {
        for c in row {
            print!("{c}");
        }
        println!();
    }
}

// < 4
