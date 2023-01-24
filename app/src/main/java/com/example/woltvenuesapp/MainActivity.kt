package com.example.woltvenuesapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.woltvenuesapp.Model.Item
import com.example.woltvenuesapp.Model.Section
import com.example.woltvenuesapp.Model.Venue
import com.example.woltvenuesapp.Model.VenueModel
import com.example.woltvenuesapp.Network.APIService
import com.google.android.gms.location.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    var locationLatitude = "60.170187"
    var locationLongitude = "24.930599"

    lateinit var venueSearchListSection: List<Section>
    lateinit var venueSearchListItem: List<Item>
    lateinit var venueSearchListVenue: List<Venue>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        //getLastLocation()

        venueSearchListSection = ArrayList<Section>()
        venueSearchListItem = ArrayList()
        venueSearchListVenue = ArrayList()
        fetchDataFromApi()



    }

    private fun fetchDataFromApi(){
        //venue results from api
        val retrofitVenueSearch = Retrofit.Builder()
            .baseUrl("https://restaurant-api.wolt.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val apiVenueSearch = retrofitVenueSearch.create(APIService::class.java)
        apiVenueSearch.getVenues(locationLatitude, locationLongitude).enqueue(object :
            Callback<VenueModel> {
            override fun onResponse(
                call: Call<VenueModel>,
                response: Response<VenueModel>
            ) {
                venueSearchListSection = response.body()?.sections ?: venueSearchListSection
                for(element in venueSearchListSection){
                    Log.e("venue", element.items.toString())
                }


            }
            override fun onFailure(call: Call<VenueModel>, t: Throwable) {
                t.message?.let { Log.e("venue", it) }
            }
        })
    }



    // ------------------------Location finding and user permissions-------------------------------------------
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        locationLatitude = location.latitude.toString()
                        locationLongitude = location.longitude.toString()
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location? = locationResult.lastLocation
            if (mLastLocation != null) {
                locationLatitude = mLastLocation.latitude.toString()
                locationLongitude = mLastLocation.longitude.toString()
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    // ------------------------Location finding and user permissions-------------------------------------------
}