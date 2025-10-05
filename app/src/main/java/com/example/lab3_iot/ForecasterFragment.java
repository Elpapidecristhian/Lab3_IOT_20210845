package com.example.lab3_iot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lab3_iot.ForecastAdapter;
import com.example.lab3_iot.ForecastItem;
import com.example.lab3_iot.R;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ForecasterFragment extends Fragment {

    private RecyclerView recyclerView;
    private ForecastAdapter adapter;
    private List<ForecastItem> forecastList;
    private static final String API_KEY = "5586a0acd5a345e0b4361158250210";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecaster, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewForecast);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        forecastList = new ArrayList<>();

        String idLocation = getArguments() != null ? getArguments().getString("idLocation") : null;
        if (idLocation != null) {
            obtenerPronostico(idLocation);
        }

        return view;
    }

    private void obtenerPronostico(String idLocation) {
        String url = "http://api.weatherapi.com/v1/forecast.json?key=" + API_KEY + "&q=id:" + idLocation + "&days=14";

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject forecast = response.getJSONObject("forecast");
                        JSONArray days = forecast.getJSONArray("forecastday");

                        for (int i = 0; i < days.length(); i++) {
                            JSONObject day = days.getJSONObject(i);
                            String date = day.getString("date");

                            JSONObject dayInfo = day.getJSONObject("day");
                            double max = dayInfo.getDouble("maxtemp_c");
                            double min = dayInfo.getDouble("mintemp_c");
                            String condition = dayInfo.getJSONObject("condition").getString("text");
                            String icon = dayInfo.getJSONObject("condition").getString("icon");

                            forecastList.add(new ForecastItem(date, max, min, condition, icon));
                        }

                        adapter = new ForecastAdapter(forecastList);
                        recyclerView.setAdapter(adapter);

                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Error al parsear datos", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getContext(), "Error en conexión", Toast.LENGTH_SHORT).show());

        queue.add(request);
    }
}
