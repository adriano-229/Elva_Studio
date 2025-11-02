package com.example.ApiExterna.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
	
	private String idPlayer;
	private String idTeam;
	private String strPlayer;
	private String strNationality;
	private String strTeam;
	private String dateBorn;
	private String strNumber;
	private String strSigning;
	private String strWage;
	private String strBirthLocation;
	private String strStatus;
	//private String strDescriptionEN;
	private String strSide;
	private String strPosition;
	private String strHeight;
	private String strWeight;
	private String strCutout;
	private String strThumb;

	 /*"idPlayer": "34163698",
     "idTeam": "133604",
     "strNationality": "England",
     "strPlayer": "Ben White",
     "strTeam": "Arsenal",
     "dateBorn": "1997-11-08",
     "strNumber": "4",
     "strSigning": "£25.20m",
     "strWage": "£6,240,000",
     "strBirthLocation": "Poole, England",
     "strStatus": "Active",
     "strDescriptionEN"
     "strSide": "Right",
     "strPosition": "Right-Back",
     "strHeight": "6 ft 1 in (1.85 m)",
     "strWeight": "78 kg",
     "strCutout": "https://r2.thesportsdb.com/images/media/player/cutout/tcxzcr1694203768.png",*/
	
}
