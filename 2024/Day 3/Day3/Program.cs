using System.Text.RegularExpressions;

namespace Day3;

internal static partial class Program
{
	private static readonly string Contents = File.ReadAllText(@"input.txt");

	public static void Main(string[] args)
	{
		Algorithm();
		Algorithm(true);
	}

	private static  void Algorithm(bool part2 = false)
	{
		var sumOfResults = 0;
		var ignoreRanges = new List<(int start, int end)>();
		var isEnabled = true;
		int? currentIgnoreStart = null;

		if (part2)
		{
			var switchingMatches = EnableRegex().Matches(Contents)
				.Select(m => (m.Index, isEnable: true))
				.Concat(DisableRegex().Matches(Contents)
					.Select(m => (m.Index, isEnable: false)))
				.OrderBy(x => x.Index)
				.ToList();

			foreach (var match in switchingMatches)
			{
				switch (match.isEnable)
				{
					case false when isEnabled:
						currentIgnoreStart = match.Index;
						isEnabled = false;
						break;
					case true when !isEnabled:
					{
						if (currentIgnoreStart.HasValue)
						{
							ignoreRanges.Add((currentIgnoreStart.Value, match.Index));
						}
						isEnabled = true;
						currentIgnoreStart = null;
						break;
					}
				}
			}
		}

		if (currentIgnoreStart.HasValue)
		{
			ignoreRanges.Add((currentIgnoreStart.Value, Contents.Length));
		}

		foreach (Match match in MultRegex().Matches(Contents))
		{
			var index = match.Index;
			if (part2 && ignoreRanges.Any(range => index >= range.start && index <= range.end))
			{
				continue;
			}

			var captures = match.Groups.Cast<Group>().Skip(1).Select(g => g.Value).ToArray();
			var numbers = captures.Select(int.Parse).ToArray();

			sumOfResults += numbers[0] * numbers[1];
		}

		Console.WriteLine(sumOfResults);
	}

	[GeneratedRegex(@"mul\(([0-9 ]*),([0-9 ]*)\)", RegexOptions.Compiled)]
	private static partial Regex MultRegex();

	[GeneratedRegex(@"do\(\)", RegexOptions.Compiled)]
	private static partial Regex EnableRegex();

	[GeneratedRegex(@"don't\(\)", RegexOptions.Compiled)]
	private static partial Regex DisableRegex();
}