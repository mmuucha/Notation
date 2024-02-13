
/** 
 * 
 *  @author Matthew Mucha
 */
public class InvalidNotationFormatException extends Exception
//
{
	public InvalidNotationFormatException(Exception e )
	{
		super(e);
	}

	public InvalidNotationFormatException(String m) 
	{
		super(m);
	}
}
