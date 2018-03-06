package httpserver.connector.nio;

import httpserver.connector.SelectChannelEndPoint;

import java.io.IOException;

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
