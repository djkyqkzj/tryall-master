package com.zmj.example.tryall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zmj.example.tryall.R;

import java.util.List;

/**
 * Created by ZMJ
 * on 2018/9/20
 */
public class WeatherRecyclerAdapter extends RecyclerView.Adapter<WeatherRecyclerAdapter.WeatherViewHolder> {

	private Context context;
	private List<String> weatherData;
	private LayoutInflater inflater;

	public WeatherRecyclerAdapter(Context context, List<String> weatherData) {
		this.context = context;
		this.weatherData = weatherData;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
	    View view = inflater.inflate(R.layout.weather_recycler_item,parent,false);
		WeatherViewHolder weatherViewHolder = new WeatherViewHolder(view);
		return weatherViewHolder;
	}

	@Override
	public void onBindViewHolder(WeatherViewHolder holder, int position) {
		final int tempos = position % (weatherData.size());
		holder.tv_weatherInfo.setText(weatherData.get(tempos));
	}

	@Override
	public int getItemCount() {
		return 100000;
	}

	class WeatherViewHolder extends RecyclerView.ViewHolder{
		TextView tv_weatherInfo;
		public WeatherViewHolder(View itemView) {
			super(itemView);
			tv_weatherInfo = itemView.findViewById(R.id.tv_weatherInfo);
		}

	}
}
