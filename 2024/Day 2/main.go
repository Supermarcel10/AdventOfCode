package main

import (
	"bufio"
	"log"
	"os"
	"strconv"
	"strings"
)

func main() {
	reports, err := readLines("C:\\Users\\Marcel\\PycharmProjects\\AoC2023\\2024\\Day 2\\input.txt")
	if err != nil {
		log.Fatalf("readLines: %s", err)
	}

	part1(reports)
	part2(reports)
}

func part1(reports []string) {
	var numOfSafeReports int
	for _, report := range reports {
		readings := strings.Split(report, " ")
		nums := make([]int, len(readings))
		for i, r := range readings {
			nums[i] = convertToInt(r)
		}

		if isReportSafe(nums, -1) {
			numOfSafeReports++
		}
	}

	println("Answer Part 1: " + strconv.Itoa(numOfSafeReports))
}

func part2(reports []string) {
	var numOfSafeReports int
	for _, report := range reports {
		readings := strings.Split(report, " ")
		nums := make([]int, len(readings))
		for i, r := range readings {
			nums[i] = convertToInt(r)
		}

		if isReportSafe(nums, -1) || hasSafeVariant(nums) {
			numOfSafeReports++
		}
	}

	println("Answer Part 2: " + strconv.Itoa(numOfSafeReports))
}

func hasSafeVariant(readings []int) bool {
	for skipIdx := range readings {
		if isReportSafe(readings, skipIdx) {
			return true
		}
	}
	return false
}

func isReportSafe(readings []int, skipIdx int) bool {
	var trend int
	var prev int
	first := true

	for i, reading := range readings {
		if i == skipIdx {
			continue
		}

		if first {
			prev = reading
			first = false
			continue
		}

		diff := absDiffInt(prev, reading)
		if diff < 1 || diff > 3 {
			return false
		}

		actualDiff := prev - reading
		if trend == 0 {
			trend = clamp(actualDiff)
		} else if (actualDiff < 0 && trend == 1) || (actualDiff > 0 && trend == -1) {
			return false
		}

		prev = reading
	}

	return true
}

func convertToInt(num string) int {
	integer, err := strconv.Atoi(num)
	if err != nil {
		panic(err)
	}

	return integer
	return 0
}

func clamp(value int) int {
	if value > 1 {
		return 1
	}
	if value < -1 {
		return -1
	}
	return value
}

func absDiffInt(x, y int) int {
	if x < y {
		return y - x
	}
	return x - y
}

func readLines(path string) ([]string, error) {
	file, err := os.Open(path)
	if err != nil {
		return nil, err
	}
	defer file.Close()

	var lines []string
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		lines = append(lines, scanner.Text())
	}

	return lines, scanner.Err()
}
