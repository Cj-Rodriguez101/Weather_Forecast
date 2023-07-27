package com.cjrodriguez.weatherforecast.util;

import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;

import com.cjrodriguez.weatherforecast.R;
import com.cjrodriguez.weatherforecast.model.Current;
import com.cjrodriguez.weatherforecast.model.Forecast;
import com.cjrodriguez.weatherforecast.model.Forecastday;
import com.cjrodriguez.weatherforecast.model.Location;
import com.cjrodriguez.weatherforecast.model.UpdatedWeatherData;

import java.util.List;
import java.util.Objects;

public class Util {

    public UpdatedWeatherData getCurrentWeatherFromForecast(Forecastday forecastday, Location location){
        return new UpdatedWeatherData(location, new Current(forecastday.getDate_epoch(),
                forecastday.getDate(), forecastday.getDay().getAvgtemp_c(),
                1, forecastday.getDay().getCondition(), forecastday.getDay().getMaxwind_kph(),
                120, "", 0.0, 0.0, 12, 12,
                12.0, 12.0, 12.0, 12.0,12.0
                ), new Forecast(0, List.of(forecastday)));
    }

    public int getSpecificDrawable(String dayOrNight, String imageId){

        if(Objects.equals(dayOrNight, "night")){
            switch (imageId){
                case "113":
                    return R.drawable._113night;
                case "116":
                    return R.drawable._116night;
                case "119":
                    return R.drawable._119night;
                case "122":
                    return R.drawable._122night;
                case "143":
                    return R.drawable._143night;
                case "176":
                    return R.drawable._176night;
                case "179":
                    return R.drawable._179night;
                case "182":
                    return R.drawable._182night;
                case "185":
                    return R.drawable._185night;
                case "200":
                    return R.drawable._200night;
                case "227":
                    return R.drawable._227night;
                case "230":
                    return R.drawable._230night;
                case "248":
                    return R.drawable._248night;
                case "260":
                    return R.drawable._260night;
                case "263":
                    return R.drawable._263night;
                case "266":
                    return R.drawable._266night;
                case "281":
                    return R.drawable._281night;
                case "284":
                    return R.drawable._284night;
                case "293":
                    return R.drawable._293night;
                case "296":
                    return R.drawable._296night;
                case "299":
                    return R.drawable._299night;
                case "302":
                    return R.drawable._302night;
                case "305":
                    return R.drawable._305night;
                case "308":
                    return R.drawable._308night;
                case "311":
                    return R.drawable._311night;
                case "314":
                    return R.drawable._314night;
                case "317":
                    return R.drawable._317night;
                case "320":
                    return R.drawable._320night;
                case "323":
                    return R.drawable._323night;
                case "326":
                    return R.drawable._326night;
                case "329":
                    return R.drawable._329night;
                case "332":
                    return R.drawable._332night;
                case "335":
                    return R.drawable._335night;
                case "338":
                    return R.drawable._338night;
                case "350":
                    return R.drawable._350night;
                case "353":
                    return R.drawable._353night;
                case "356":
                    return R.drawable._356night;
                case "359":
                    return R.drawable._359night;
                case "362":
                    return R.drawable._362night;
                case "365":
                    return R.drawable._365night;
                case "368":
                    return R.drawable._368night;
                case "371":
                    return R.drawable._371night;
                case "374":
                    return R.drawable._374night;
                case "377":
                    return R.drawable._377night;
                case "386":
                    return R.drawable._386night;
                case "389":
                    return R.drawable._389night;
                case "392":
                    return R.drawable._392night;
                default:
                    return R.drawable._395night;
            }
        } else {
            switch (imageId){
                case "113":
                    return R.drawable._113;
                case "116":
                    return R.drawable._116;
                case "119":
                    return R.drawable._119;
                case "122":
                    return R.drawable._122;
                case "143":
                    return R.drawable._143;
                case "176":
                    return R.drawable._176;
                case "179":
                    return R.drawable._179;
                case "182":
                    return R.drawable._182;
                case "185":
                    return R.drawable._185;
                case "200":
                    return R.drawable._200;
                case "227":
                    return R.drawable._227;
                case "230":
                    return R.drawable._230;
                case "248":
                    return R.drawable._248;
                case "260":
                    return R.drawable._260;
                case "263":
                    return R.drawable._263;
                case "266":
                    return R.drawable._266;
                case "281":
                    return R.drawable._281;
                case "284":
                    return R.drawable._284;
                case "293":
                    return R.drawable._293;
                case "296":
                    return R.drawable._296;
                case "299":
                    return R.drawable._299;
                case "302":
                    return R.drawable._302;
                case "305":
                    return R.drawable._305;
                case "308":
                    return R.drawable._308;
                case "311":
                    return R.drawable._311;
                case "314":
                    return R.drawable._314;
                case "317":
                    return R.drawable._317;
                case "320":
                    return R.drawable._320;
                case "323":
                    return R.drawable._323;
                case "326":
                    return R.drawable._326;
                case "329":
                    return R.drawable._329;
                case "332":
                    return R.drawable._332;
                case "335":
                    return R.drawable._335;
                case "338":
                    return R.drawable._338;
                case "350":
                    return R.drawable._350;
                case "353":
                    return R.drawable._353;
                case "356":
                    return R.drawable._356;
                case "359":
                    return R.drawable._359;
                case "362":
                    return R.drawable._362;
                case "365":
                    return R.drawable._365;
                case "368":
                    return R.drawable._368;
                case "371":
                    return R.drawable._371;
                case "374":
                    return R.drawable._374;
                case "377":
                    return R.drawable._377;
                case "386":
                    return R.drawable._386;
                case "389":
                    return R.drawable._389;
                case "392":
                    return R.drawable._392;
                default:
                    return R.drawable._395;
            }
        }
    }
}
