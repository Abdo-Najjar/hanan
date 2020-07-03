
package views;

/**
 *
 * @author abdo
 */
public class ComboItem {
    private final String key;
    private final String value;

    public ComboItem( String value , String key)
    {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString()
    {
        return key;
    }

    public String getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }
}
