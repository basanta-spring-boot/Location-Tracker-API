package com.google.location.track.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.location.track.api.common.Constants;
import com.google.location.track.api.dto.DurationTimeWrapper;
import com.google.location.track.api.dto.Location;
import com.google.location.track.api.dto.LocationResponse;
import com.google.location.track.api.search.dto.NearestSearchResponse;
import com.google.location.track.api.util.RuleUtil;

@Controller
@RequestMapping(value = "/GMap")
public class LocationTraceController {
	@Autowired
	private RuleUtil ruleUtil;

	Logger log = LoggerFactory.getLogger(LocationTraceController.class);

	@RequestMapping(value = "/getLocation")
	public String getAddress(@RequestParam("lattitude") double lattitude,
			@RequestParam("longitude") double longitude, Model model) {
		LocationResponse response = null;
		try {
			response = ruleUtil.getAddressDetails(lattitude, longitude);
			String address = response.getResults().get(0).getFormattedAddress();
			model.addAttribute("address", address);
		} catch (Exception e) {
			model.addAttribute("errorMessage", Constants.ERROR_MESSAGE);
			log.error(e.getLocalizedMessage());
		}
		return "getAddress";
	}

	@RequestMapping(value = "/getDistanceTime")
	public String getDistanceTime(String source, String destination, Model model) {
		DurationTimeWrapper response = null;
		try {
			response = ruleUtil.getDuration(source, destination);
			model.addAttribute("response", response);
		} catch (Exception e) {
			model.addAttribute("errorMessage", Constants.ERROR_MESSAGE);
			log.error(e.getLocalizedMessage());
		}
		return "getDuration";

	}

	@RequestMapping(value = "/getLatitudeLongitude")
	public String getLatitudeLongitude(String address, Model model) {
		Location location = null;
		try {
			location = ruleUtil.getLatitudeLongitude(address);
			model.addAttribute("location", location);
		} catch (Exception e) {
			model.addAttribute("errorMessage", Constants.ERROR_MESSAGE);
			log.error(e.getLocalizedMessage());
		}
		return "getLatLang";
	}

	@RequestMapping(value = "/searchNearestPlace")
	public String getNearestPlace(
			@RequestParam("searchType") String searchType,
			@RequestParam("location") String location, Model model) {
		List<NearestSearchResponse> searchResponses = null;
		try {
			searchResponses = ruleUtil.getNearestPlace(searchType, location);
			model.addAttribute("searchResponses", searchResponses);
		} catch (Exception e) {
			model.addAttribute("errorMessage", Constants.ERROR_MESSAGE);
			log.error(e.getLocalizedMessage());
		}
		return "searchResault";

	}
}
