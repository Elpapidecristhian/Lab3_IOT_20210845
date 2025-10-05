package com.example.lab3_iot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateFragment extends Fragment {

    private EditText etCiudad, etFecha;
    private Button btnBuscar;
    private RecyclerView recyclerView;
    private ForecastAdapter adapter;
    private List<ForecastItem> forecastList;
    private static final String API_KEY = "5586a0acd5a345e0b4361158250210";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date, container, false);

        etCiudad = view.findViewById(R.id.etCiudad);
        etFecha = view.findViewById(R.id.etFecha);
        btnBuscar = view.findViewById(R.id.btnBuscarFecha);
        recyclerView = view.findViewById(R.id.recyclerViewDate);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        forecastList = new ArrayList<>();

        btnBuscar.setOnClickListener(v -> {
            String ciudad = etCiudad.getText().toString().trim();
            String fecha = etFecha.getText().toString().trim();

            if (ciudad.isEmpty() || fecha.isEmpty()) {
                Toast.makeText(getContext(), "Complete ambos campos", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                LocalDate inputDate = LocalDate.parse(fecha);
                LocalDate today = LocalDate.now();
                String endpoint = inputDate.isAfter(today) ? "future" : "history";
                buscarPronostico(ciudad, fecha, endpoint);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Formato de fecha inválido (YYYY-MM-DD)", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void buscarPronostico(String ciudad, String fecha, String tipo) {
        String url = "https://api.weatherapi.com/v1/" + tipo + ".json?key=" + API_KEY + "&q=" + ciudad + "&dt=" + fecha;

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        forecastList.clear();

                        JSONObject forecastObj = response.getJSONObject("forecast")
                                .getJSONArray("forecastday").getJSONObject(0);
                        JSONObject day = forecastObj.getJSONObject("day");

                        double maxTemp = day.getDouble("maxtemp_c");
                        double minTemp = day.getDouble("mintemp_c");
                        String condition = day.getJSONObject("condition").getString("text");
                        String icon = day.getJSONObject("condition").getString("icon");

                        forecastList.add(new ForecastItem(fecha, maxTemp, minTemp, condition, icon));

                        adapter = new ForecastAdapter(forecastList);
                        recyclerView.setAdapter(adapter);

                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Error al procesar datos", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getContext(), "Error al conectar con la API", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }
}
