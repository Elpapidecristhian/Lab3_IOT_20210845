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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LocationFragment extends Fragment {

    private EditText etSearch;
    private Button btnSearch;
    private RecyclerView recyclerView;
    private LocationAdapter adapter;
    private List<LocationItem> locationList;

    private static final String API_KEY = "5586a0acd5a345e0b4361158250210";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        etSearch = view.findViewById(R.id.etSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        locationList = new ArrayList<>();

        adapter = new LocationAdapter(locationList, item -> {
            Bundle bundle = new Bundle();
            bundle.putString("idLocation", item.getId());

            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_locationFragment_to_forecasterFragment, bundle);
        });

        recyclerView.setAdapter(adapter);

        btnSearch.setOnClickListener(v -> {
            String query = etSearch.getText().toString().trim();
            if (query.isEmpty()) {
                Toast.makeText(getContext(), "Ingrese una ciudad o distrito", Toast.LENGTH_SHORT).show();
            } else {
                fetchLocations(query);
            }
        });

        return view;
    }

    private void fetchLocations(String query) {
        String url = "https://api.weatherapi.com/v1/search.json?key=" + API_KEY + "&q=" + query;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    locationList.clear();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            String name = obj.getString("name");
                            String region = obj.getString("region");
                            String country = obj.getString("country");
                            String id = obj.getString("id");

                            locationList.add(new LocationItem(id, name, region, country));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Error al procesar datos", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show()
        );

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(request);
    }
}
