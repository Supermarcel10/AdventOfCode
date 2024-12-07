[TAKE ME BACK](../README.md)

# 2024

## Polyglot Challenge

### What is a polyglot?

A polyglot is someone who speaks and understands several natural languages.

### Overview

This repository contains my solutions for Advent of Code 2024, with a twist - I'm solving each day's problem in a different programming language!
This challenge helps explore various programming paradigms and language features while solving interesting algorithmic puzzles.

### Reasoning

There are many motives behind this challenge. For me these are:
- Exploring other languages (seeing what I like and don't like)
- Building up understanding of concepts and how they are implemented from language to language
- Proving my ability to independently research and piece together a solution (albeit very hacky solutions)
- Learning different programming principles, for e.g. different implementations of memory management (Rust scope-based system, C++ pointer-based system or standard GC)
- Learning to think more outside the box, correlate languages and what they do differently, and use that knowledge to my day-to-day development
- Proving my flexibility with working with what I have

Many people have commented that this challenge is somewhat pointless; I should just learn one language to a deep extent.
As much as understanding a language to a great extent is good, I strongly disagree with this statement.
Most languages are the same in terms of the principals and concepts they make use of, with a different syntax, structures, methods, etc. provided.

I believe that a good/great programmer should be able to work with what they have, be flexible and have implementation ideas correlated to different language principles.
This is what I believe differentiates an average programmer from a good programmer.

### Rules

- Use a different programming language for each day
- Complete both parts of each day's puzzle in the same language
- A language cannot be reused (25 unique languages)
- External sources like StackOverflow, Documentation or any public code use is allowed
- AI use of any kind is not allowed

## Language Complexity

My personal definition of which languages are more complex than others.
The languages I have previously worked with highly influence this!

For the reasoning, see the day related to the language.

The lower the complexity, the simpler the language is to learn.

| Language | Day | Complexity  |
|:--------:|:---:|:------------|
|  Python  |  1  | ★           |
|    C#    |  3  | ★ ⯨         |
|   Java   |  6  | ★ ⯨         |
|   C++    |  5  | ★ ★         |
| Haskell  |  4  | ★ ★ ⯨       |
|  GoLang  |  2  | ★ ★ ★ ★     |
|          |  -  | ★ ★ ★ ★ ★ ⯨ |



## Day 1: Historian Hysteria
**Language**: `PYTHON` ★

This challenge posed no complexity with a Python implementation for me.
Python is a pretty standard language, which I've previously used for some projects.
Throughout the whole challenge, I sort of expected what I would do and how I'd implement it.

That said, it has been a while since I last touched python - this exercise was a great reminder.
One thing I misremembered, is that Python has the `++` operator on an integer variable like many other languages.
However, this wasn't a complex thing, but more of a cleanliness preference thing.

As far as my implementation goes, it is definitely not the most efficient - as part of this challenge the aim isn't to get the least complexity in each language, but rather to use each language to experiment with it.
Looking back at it, there are some python features that I could have used a bit better - the list could have been `sorted()` instead of using a `sort()` to sort it in place for cleaner code.
The `distances` variable also is completely pointless, my assumption that it would come in handy for part 2 was wrong, and since I was able to write part 2 with no refactor required, I ended up not refactoring the file at all.

## Day 2: Red-Nosed Reports
**Language**: `GO LANG` ★ ★ ★ ★

Never having worked with GoLang, this was extremely difficult for me.
The concepts of GoLang seem very weird, and certain aspects of the language don't make much sense to me - perhaps if I worked with the language a bit more, I'd be able to get a clearer grasp.

To me, it seems like GoLang attempts to emit a lot of things that Rust does with error handling - by ensuring it gets handled in every case. One example of this is when opening the input file.
The only problem is, it simply does it worse than Rust does.

While writing this challenge I also originally did not understand the concept of the difference between `=` and `:=`.
A [StackOverflow](https://stackoverflow.com/questions/17891226/difference-between-and-operators-in-go) post clarified it.
I did not understand the differences between the two, and originally tried doing things like `var example := 0`.
This also doesn't seem coherent with a lot of other languages out there.

While working on this, I realised there is a math package (it took me a while to find it, since the StackOverflow posts were above).
The math package is very stripped down, and lacks most basic functionality like clamping a number between two numbers (I wrote `clamp()` [LINE 108](Day%202/main.go#L108) for this with my hard coded variables).
The math package does in fact have an `Abs()` function, but it's signature only takes float64 - I'd have to convert my integers into floating points?
I don't understand why the inputs wouldn't be generic as in most other languages, which just adds unnecessary complexity, and potential for other issues with the use of only floating points.

## Day 3: Mull It Over
**Language**: `C#` ★ ⯨



## Day 4: Ceres Search
**Language**: `Haskell` ★ ★ ⯨



## Day 5: Print Queue
**Language**: `C++` ★ ★



## Day 6: Guard Gallivant
**Language**: `Java` ★ ⯨



## Day 7: TBD
**Language**: `TBD` ★ ⯨


