package lt.mm.moviedb.network;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import lt.mm.moviedb.entities.SearchList;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;

import java.io.IOException;

/**
 * Created by mariusmerkevicius on 7/26/15.
 */
public class JsonRequest<Type> extends Request<Type> {
    Response.Listener<Type> successListener;

    private final Class classType;

    public JsonRequest(Class classType, int method, String url, Response.Listener<Type> successListener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.successListener = successListener;
        this.classType = classType;
    }

    @Override
    protected Response<Type> parseNetworkResponse(NetworkResponse response) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ObjectReader objectReader = mapper.reader(classType);
        try {
            Object result = objectReader.readValue(response.data);
            if (result != null)
                return Response.success((Type)result, HttpHeaderParser.parseCacheHeaders(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.error(new VolleyError("Failed to parse entity!"));
    }

    @Override
    protected void deliverResponse(Type response) {
        successListener.onResponse(response);
    }

}
