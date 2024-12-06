#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <map>
#include <cstdint>
#include <sstream>
#include <unordered_map>
#include <unordered_set>
#include <queue>

using namespace std;
using uint = uint_fast32_t;


vector<string> read_file() {
    vector<string> lines;
    string line;
    ifstream file("C:\\Users\\Marcel\\PycharmProjects\\AoC2023\\2024\\Day 5\\input.txt");

    while (getline(file, line)) {
        lines.push_back(line);
    }

    return lines;
}

vector<string> split(const string& str, char delimiter) {
    vector<string> tokens;
    string token;
    istringstream tokenStream(str);

    while (getline(tokenStream, token, delimiter)) {
        tokens.push_back(token);
    }

    return tokens;
}

void part_A(const vector<vector<uint>>& page_numbers,
            const map<uint, vector<uint>>& ordering_rules) {
    uint total = 0;

    for (const auto& row : page_numbers) {
        bool valid = true;

        unordered_map<uint, size_t> positions;
        for (size_t i = 0; i < row.size(); ++i) {
            positions[row[i]] = i;
        }

        for (const auto& rule : ordering_rules) {
            uint before_num = rule.first;

            if (positions.find(before_num) == positions.end()) {
                continue;
            }

            for (uint after_num : rule.second) {
                if (positions.find(after_num) == positions.end()) {
                    continue;
                }

                if (positions[before_num] >= positions[after_num]) {
                    valid = false;
                    break;
                }
            }

            if (!valid) break;
        }

        if (valid) {
            auto middle_number = row[row.size() / 2];
            total += middle_number;
        }
    }
}

vector<uint> sort(const unordered_map<uint, unordered_set<uint>>& graph,
                  const unordered_map<uint, unordered_set<uint>>& incoming,
                  const unordered_set<uint>& pages) {
    vector<uint> result;
    queue<uint> q;
    unordered_map<uint, unordered_set<uint>> in_edges = incoming;

    for (uint page : pages) {
        if (in_edges.find(page) == in_edges.end() || in_edges[page].empty()) {
            q.push(page);
        }
    }

    while (!q.empty()) {
        uint current = q.front();
        q.pop();
        result.push_back(current);

        if (graph.find(current) != graph.end()) {
            for (uint next : graph.at(current)) {
                in_edges[next].erase(current);
                if (in_edges[next].empty()) {
                    q.push(next);
                }
            }
        }
    }

    return result;
}

void solve(const vector<vector<uint>>& page_numbers,
           const map<uint, vector<uint>>& ordering_rules) {
    uint total = 0;
    uint incorrect_total = 0;
    vector<vector<uint>> fixed_rows;

    for (const auto& row : page_numbers) {
        bool valid = true;
        unordered_map<uint, size_t> positions;
        for (size_t i = 0; i < row.size(); ++i) {
            positions[row[i]] = i;
        }

        for (const auto& rule : ordering_rules) {
            uint before_num = rule.first;
            if (positions.find(before_num) == positions.end()) continue;

            for (uint after_num : rule.second) {
                if (positions.find(after_num) == positions.end()) continue;
                if (positions[before_num] >= positions[after_num]) {
                    valid = false;
                    break;
                }
            }
            if (!valid) break;
        }

        if (valid) {
            total += row[row.size() / 2];
            fixed_rows.push_back(row);
        } else {
            unordered_map<uint, unordered_set<uint>> graph;
            unordered_map<uint, unordered_set<uint>> incoming;
            unordered_set<uint> pages(row.begin(), row.end());

            for (const auto& rule : ordering_rules) {
                auto before_page = rule.first;
                auto after_pages = rule.second;

                if (pages.find(before_page) != pages.end()) {
                    for (uint after_page : after_pages) {
                        if (pages.find(after_page) != pages.end()) {
                            graph[before_page].insert(after_page);
                            incoming[after_page].insert(before_page);
                        }
                    }
                }
            }

            vector<uint> sorted = sort(graph, incoming, pages);

            if (sorted.size() == row.size()) {
                incorrect_total += sorted[sorted.size() / 2];
                fixed_rows.push_back(sorted);
            } else {
                incorrect_total += row[row.size() / 2];
                fixed_rows.push_back(row);
            }
        }
    }

    cout << total << endl;
    cout << incorrect_total << endl;
}

int main() {
    auto data = read_file();

    map<uint, vector<uint>> ordering_rules;
    vector<vector<uint>> page_numbers;

    bool is_rule = true;
    for (const auto& l : data) {
        if (l.empty()) {
            is_rule = false;
            continue;
        }

        if (is_rule) {
            auto temp = split(l, '|');
            if (temp.size() == 2) {
                ordering_rules[stoi(temp[0])].push_back(stoi(temp[1]));
            }
        } else {
            auto temp = split(l, ',');
            vector<uint> row;
            for (const auto& v : temp) {
                row.push_back(stoi(v));
            }
            page_numbers.push_back(row);
        }
    }

    solve(page_numbers, ordering_rules);

    return 0;
}
