package com.Tavisca.TaviscaMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class HomeController {

	@RequestMapping("/getVenue")
	public String getVenue(@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "category", required = false) String cat, Map<String, Object> model) {
		if (city == null || city.isEmpty() || cat == null || cat.isEmpty()) {
			return "getVenue";
		}

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.foursquare.com/v2/venues/explore")
				.queryParam("client_id", "OLWE2UV0ORCGXJWHB32GXXHYM4TMURJT0DFNSYBQIJWJ15PT")
				.queryParam("client_secret", "1FCWQHRUO3CJ2Q50SOVFQ5TYYNQTIQIQFBVI5KZTBOZSXQJQ")
				.queryParam("query", cat.trim()).queryParam("near", city.trim()).queryParam("v", "20190519");

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
				String.class);

		List<List<String>> ls = new ArrayList<>();
		parseJson(response, ls);
		if (ls.isEmpty() || ls.get(0).isEmpty()) {
			model.put("success", "No result found with given venue or catagory. Please check and try again with correct values.");
		}else {
			model.put("success", "Found below result.");
		}
		model.put("inf", ls);

		return "getVenue";
	}

	private void parseJson(HttpEntity<String> response, List<List<String>> ls) {
		try {
			JsonNode tree = new ObjectMapper().readTree(response.getBody());
			JsonNode groupNode = tree.at("/response/groups");
			if (groupNode.isArray()) {

				System.out.println("Is this node an Array? " + groupNode.isArray());

				for (JsonNode node : groupNode) {
					JsonNode items = node.at("/items");
					for (JsonNode itemsNode : items) {
						List<String> subls = new ArrayList<>();
						subls.add(itemsNode.at("/venue/name").asText());
						subls.add(itemsNode.at("/venue/location/address").asText());
						subls.add(itemsNode.at("/venue/location/city").asText());
						subls.add(itemsNode.at("/venue/location/state").asText());

						JsonNode cats = itemsNode.at("/venue/categories");
						StringBuilder sb = new StringBuilder("");
						for (JsonNode catNode : cats) {
							sb.append(catNode.at("/name").asText() + ", ");
						}
						subls.add(sb.toString());
						ls.add(subls);
						subls.add("lat: " + itemsNode.at("/venue/location/lat").asText() + " lang: "
								+ itemsNode.at("/venue/location/lat").asText());
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}