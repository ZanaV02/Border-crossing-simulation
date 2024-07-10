package Main.Terminals;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectOutputStream2 extends ObjectOutputStream
{
    public ObjectOutputStream2(OutputStream outputStream) throws java.io.IOException
    {
        super(outputStream);
    }
    @Override
    protected void writeStreamHeader() throws java.io.IOException
    {
        reset();
    }
}
