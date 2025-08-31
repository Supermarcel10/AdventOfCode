import {readFileSync} from "fs";


const file = readFileSync("./../input.txt", "utf-8");
let sum = 0;

for (const line of file.split("\n")) {
	if (line == null) {
		continue;
	}

	const [target, ...nums] = line.split(/: | /).map(Number);

	let options = [nums[0]];

	for (let i = 1; i < nums.length; ++i) {
		const num = nums[i];
		const nextOptions = [];

		for (const option of options) {
			nextOptions.push(option + num);
			nextOptions.push(option * num);
			nextOptions.push(parseInt(option.toString() + num)); // Part 2
		}

		options = nextOptions.filter(option => option <= target);
	}

	if (options.some(option => option === target)) {
		sum += target;
	}
}

console.log(sum);
