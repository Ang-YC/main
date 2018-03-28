package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RatingSortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RatingSortCommand object
 */
public class RatingSortCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the RatingSortCommand
     * and returns an RatingSortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RatingSortCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SORT_ORDER);

        if (!arePrefixesPresent(argMultimap, PREFIX_SORT_ORDER)
                || !areAllFieldsSupplied(argMultimap, PREFIX_SORT_ORDER)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RatingSortCommand.MESSAGE_USAGE));
        }


        RatingSortCommand.SortOrder sortOrder;
        try {
            sortOrder = ParserUtil.parseSortOrder(
                    argMultimap.getValue(PREFIX_SORT_ORDER)).get();
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
        return new RatingSortCommand(sortOrder);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static boolean areAllFieldsSupplied(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> !Optional.of(argumentMultimap.getValue(prefix)).equals(""));
    }
}
