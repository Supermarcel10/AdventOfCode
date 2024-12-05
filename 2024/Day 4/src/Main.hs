import System.IO


part1 :: Input -> Int
part1 input = length $ findAllPatternsFromX input "XMAS"


part2 :: Input -> Int
part2 input = length $ findXPattern input


findXPattern :: Input -> [Pos]
findXPattern grid =
    [(r,c) |
        r <- [1..length grid - 2],
        c <- [1..length (head grid) - 2],
        (grid !! r) !! c == 'A',
        let tl = (grid !! (r - 1)) !! (c - 1)
            tr = (grid !! (r + 1)) !! (c + 1)
            bl = (grid !! (r + 1)) !! (c - 1)
            br = (grid !! (r - 1)) !! (c + 1)
        in isValidDiagonalPair tl tr br bl
    ]
    where
        isValidDiagonalPair tl tr br bl =
            (isValidDiagonal [tl, tr] && isValidDiagonal [br, bl])
            || (isValidDiagonal [br, bl] && isValidDiagonal [tl, tr])

        isValidDiagonal [c1, c2] =
            (c1 == 'M' && c2 == 'S')
            || (c1 == 'S' && c2 == 'M')


findPattern :: Input -> Pos -> String -> [Pos]
findPattern grid (row, col) pattern
    | row < 0 || row >= length grid = []
    | col < 0 || col >= length (head grid) = []
    | otherwise = concatMap checkDirection directions
    where
        directions = [(0, 1), (1, 0), (0, -1), (-1, 0),
            (1, 1), (1, -1), (-1, 1), (-1, -1)]

        checkDirection (dx, dy) =
            if matchesDirection (row, col) pattern
            then [(row, col)]
            else []
            where
                matchesDirection :: Pos -> String -> Bool
                matchesDirection (r, c) [] = True
                matchesDirection (r, c) (p:ps) =
                    let nextR = r + dx
                        nextC = c + dy
                    in r >= 0 && r < length grid &&
                       c >= 0 && c < length (head grid) &&
                       (grid !! r) !! c == p &&
                       matchesDirection (nextR, nextC) ps

findFirstCharOfPattern :: Input -> Char -> [Pos]
findFirstCharOfPattern grid ch =
    [(r, c) |
        r <- [0..length grid - 1],
        c <- [0..length (head grid) - 1],
        (grid !! r) !! c == ch
    ]

findAllPatternsFromX :: Input -> String -> [Pos]
findAllPatternsFromX grid pattern = concat [findPattern grid pos pattern | pos <- findFirstCharOfPattern grid 'X']


type Input = [String]
type Pos = (Int, Int)

main :: IO ()
main = do
    s <- readFile ".\\input.txt"
    let input = [l | l <- lines s]
    print $ part1 input
    print $ part2 input
