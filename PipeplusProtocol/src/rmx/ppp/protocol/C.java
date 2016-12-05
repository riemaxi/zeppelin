/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.ppp.protocol;

/**
 *
 * @author Samuel
 */
public interface C {
    int PARAMETER_PAUSE_TIME = 250;
    String PARAMETER_PAUSE_TIME_NAME = "rmx.ppp.protocol.pause_time";
    
    String PARAMETER_FILE = "parameter.txt";
    String PARAMETER_COMMENT = "#";
    String PARAMETER_DELIMITER = ":";
    
    String SERIAL_JOINT_ID = "rmx.ppp.serialjoint";
    String PAR_OR_JOINT_ID = "rmx.ppp.par_or_joint";
    String PAR_AND_JOINT_ID = "rmx.ppp.par_and_joint";
    
    String PARSER_PAR_AND_OPEN = "{";
    String PARSER_PAR_AND_CLOSE= "}";
    String PARSER_PAR_OR_OPEN = "[";
    String PARSER_PAR_OR_CLOSE= "]";
    String PARSER_SERIAL_OPEN = "(";
    String PARSER_SERIAL_CLOSE= ")";
    String PARSER_COMMENT_OPEN = "<";
    String PARSER_COMMENT_CLOSE = ">";
    String PARSER_COMMENT_HASH_OPEN = "#";
    String PARSER_COMMENT_HASH_CLOSE = "\n";
    String PARSER_SYMBOL = "SYMBOL";
    String PARSER_BLOCK_CHARS = "{}()[]<>#";
    
    String PARSER_STATE_ERROR = "ERROR";
    String PARSER_STATE_START = "START";
    String PARSER_STATE_BLOCK_PAR_AND = "BLOCK_AND";
    String PARSER_STATE_BLOCK_PAR_OR = "BLOCK_OR";
    String PARSER_STATE_BLOCK_SERIAL = "BLOCK_SERIAL";
    
    int PARSER_SYMBOL_LEFT_BEHIND = 0;

    String PARSER_ERROR_UNEXPECTED = "ui.message.error.token_unexptected";
    String PARSER_ERROR_BLOCK_NOT_CLOSED = "ui.message.error.block_not_closed";
    String PARSER_ERROR_COMMENT_NOT_CLOSED = "ui.message.error.comment_not_closed";
    String PARSER_ERROR_UKNOWN = "ui.message.error.unknown_error";
    String PARSER_ERROR_SYMBOL_NOT_FOUND = "ui.message.error.symbol_not_found";
}
