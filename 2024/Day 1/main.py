f = open("input.txt", "r")

list1 = []
list2 = []

# Process input data
lines = f.readlines()
for line in lines:
    split = line.split("   ")
    list1.append(split[0])
    list2.append(split[1])

# Sort data from smallest to largest
list1.sort()
list2.sort()

# Find distances
length1 = len(list1)
length2 = len(list2)

if length1 != length2:
    raise Exception("List length mismatch!")

distances = []
diff_sum = 0
for i in range(length1):
    diff = abs(int(list1[i]) - int(list2[i]))
    diff_sum += diff
    distances.append(diff)

# Part 1 Answer
print("Part 1 Answer: " + str(diff_sum))


# Part 2
similarity_score = 0
for item1 in list1:
    num_of_occurrences = 0

    for item2 in list2:
        if int(item2) == int(item1):
            num_of_occurrences += 1

        if int(item2) > int(item1):
            break

    similarity_score += int(item1) * num_of_occurrences

# Part 2 Answer
print("Part 2 Answer: " + str(similarity_score))
