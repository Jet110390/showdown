package com.example.showdown.ui.view

import androidx.appcompat.app.AppCompatActivity
import com.example.showdown.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main)

/** features i'd like to add
 * height comparison to trainer
 * weight comparison to trainer
 * implement better design for main screen buttons(hamburger menu?) and sorting options/generations(spinner?)
 * who's that pokemon feature
 *      implement easy mode(two options) and hard mode(fill in the blank)
 *      should be able to switch between views on button click
 *      add wtp streak(maybe put this in profile fragment and game fragment along with most consecutively)
 *      make it so that hard mode isnt case sensitive
 * team builder feature
 *      battle notes for each team?
 * draw feature?
 * user profile that has user height, weight, location, trainer class and partner pokemon
 * chat for trading and battles?
 * link in webview to bulbapedia DONE
 *      account for names with hyphens or aren't accurate for bulbapedia search
 * add facebook shimmer for loading items DONE
 * link to smogon
 * make dex entry scrollable
 *      implement better dex entry design
 * modularize?
 * sort pokemon by name, id, base stat, height, weight
 * if pokemon is already in the favorites list remove the like button
 */
