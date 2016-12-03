/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.ppp;

/**
 *
 * @author Samuel
 */
public interface C {
    int STATE_SUCCESS = 1000;
    int STATE_STARTED = 0;
    int STATE_ABORTED = -1000;
    
    int PARAMETER_PAUSE_TIME = 250;
    
    String PARAMETER_FILE = "parameter.txt";
    String PARAMETER_COMMENT = "#";
    String PARAMETER_DELIMITER = ":";
    
    String PARSER_PAR_AND_OPEN = "{";
    String PARSER_PAR_AND_CLOSE= "}";
    String PARSER_PAR_OR_OPEN = "[";
    String PARSER_PAR_OR_CLOSE= "]";
    String PARSER_SERIAL_OPEN = "(";
    String PARSER_SERIAL_CLOSE= ")";
    String PARSER_COMMENT_OPEN = "<";
    String PARSER_COMMENT_CLOSE = ">";
    String PARSER_SYMBOL = "SYMBOL";
    String PARSER_BLOCK_CHARS = "{}()[]<>";
    
    String PARSER_STATE_ERROR = "0";
    String PARSER_STATE_START = "START";
    String PARSER_STATE_BLOCK_PAR_AND = "BLOCK_AND";
    String PARSER_STATE_BLOCK_PAR_OR = "BLOCK_OR";
    String PARSER_STATE_BLOCK_SERIAL = "BLOCK_SERIAL";
    
    String PARSER_CONTENT_PATTERN_COMMENT = "[-#\\.]+";    
    String PARSER_CONTENT_PATTERN =  "[a-zA-Z_][a-zA-Z0-9]+|[{}\\[\\]\\(\\)<>]|" + C.PARSER_CONTENT_PATTERN_COMMENT;
    
    String SERIAL_JOINT_ID = "rmx.pipeplus.serialjoint";
    String PAR_OR_JOINT_ID = "rmx.pipeplus.par_or_joint";
    String PAR_AND_JOINT_ID = "rmx.pipeplus.par_and_joint";
    
    String PARSER_ERROR_UNEXPECTED = "ui.message.error.token_unexptected";
    String PARSER_ERROR_BLOCK_NOT_CLOSED = "ui.message.error.block_not_closed";
    String PARSER_ERROR_COMMENT_NOT_CLOSED = "ui.message.error.comment_not_closed";
    String PARSER_ERROR_UKNOWN = "ui.message.error.unknown_error";
    String PARSER_ERROR_SYMBOL_NOT_FOUND = "ui.message.error.symbol_not_found";
}
