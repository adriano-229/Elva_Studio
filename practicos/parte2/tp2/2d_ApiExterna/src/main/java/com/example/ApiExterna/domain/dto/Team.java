package com.example.ApiExterna.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
	
	private String idTeam;
	private String strTeam;
	private String strTeamAlternate;
	private String strStadium;
	private String strLocation;
	private String intStadiumCapacity;
	private String strGender;
	private String strCountry;
	private String strBadge;
	private String strTeamFanart1;
	private String strLogo;
	
	
}

/*
"strTeam": "Arsenal",
"strTeamAlternate": "Arsenal Football Club, AFC, Arsenal FC",
"intFormedYear": "1892",
"strStadium": "Emirates Stadium",
"strLocation": "Holloway, London, England",
"intStadiumCapacity": "60338",
"strGender": "Male",
"strCountry": "England",
"strBadge": "https://r2.thesportsdb.com/images/media/team/badge/uyhbfe1612467038.png",
"strLogo": "https://r2.thesportsdb.com/images/media/team/logo/q2mxlz1512644512.png",*/
