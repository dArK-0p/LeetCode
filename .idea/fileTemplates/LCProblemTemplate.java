#if (${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

import org.leetcode.core.LC;
import java.util.List;

/**
 * LeetCode ${ID}: ${TITLE}
 *
 * @see <a href="https://leetcode.com/problems/${SLUG}/">Problem Link</a>
 */
public class LC${ID} extends LC<${I}, ${T}, ${O}> {

    private static final LC${ID} INSTANCE = new LC${ID}();

    public static LC${ID} getInstance() {
        return INSTANCE;
    }

    private LC${ID}() {
        super();
    }

    @Override
    protected ${I} convert(${T} rawInput) {
        // TODO: Implement input conversion logic
        return null;
    }

    @Override
    public ${T} parseInput(String rawInput) {
        // TODO: Parse input from JSON/string to ${T}
        return null;
    }

    @Override
    public ${O} parseExpectedOutput(String rawOutput) {
        // TODO: Parse expected output from JSON/string to ${O}
        return null;
    }

    @Override
    public boolean compare(${O} expected, ${O} actual) {
        // TODO: Implement comparison logic
        return expected.equals(actual);
    }

    @Override
    public ${O} solve(${I} input) {
        // TODO: Implement the core algorithm here
        return null;
    }
}
