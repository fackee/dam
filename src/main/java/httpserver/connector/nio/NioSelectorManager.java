package httpserver.connector.nio;

import httpserver.connector.Connection;
import httpserver.connector.SelectChannelEndPoint;

/**
 * Created by geeche on 2018/2/3.
 */
public class NioSelectorManager extends SelectorManager{

    public NioSelectorManager(){

    }

    @Override
    public void endPointUpgraded(SelectChannelEndPoint selectChannelEndPoint, Connection old) {

    }
}
