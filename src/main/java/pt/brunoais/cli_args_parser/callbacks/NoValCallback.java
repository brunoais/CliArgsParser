package pt.brunoais.cli_args_parser.callbacks;

@FunctionalInterface
public interface NoValCallback extends ValCallback{
	default void c(String val1, String val2, String val3){
		callback();
	}
	
	void callback();
}
