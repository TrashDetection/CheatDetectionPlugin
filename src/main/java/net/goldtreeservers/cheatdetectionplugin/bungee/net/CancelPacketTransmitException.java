package net.goldtreeservers.cheatdetectionplugin.bungee.net;

public class CancelPacketTransmitException extends RuntimeException
{
	private static final long serialVersionUID = 4794561501267972735L; 
	
	public static final CancelPacketTransmitException INSTANCE = new CancelPacketTransmitException();
	
	private CancelPacketTransmitException()
	{
		
	}
	
	@Override
    public Throwable initCause(Throwable cause)
    {
		//Do nothing pretty much here
		
        return this;
    }
	
	@Override
	public Throwable fillInStackTrace()
	{
		//Do nothing pretty much here
		
		return this;
	}
}
