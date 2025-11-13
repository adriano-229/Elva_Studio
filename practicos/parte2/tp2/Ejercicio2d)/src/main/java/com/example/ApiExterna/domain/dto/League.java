package com.example.ApiExterna.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class League {
	
	private String idLeague;
	private String strLeague;
	private String strLeagueAlternate;
	private String strCurrentSeason;
	private String intFormedYear;
	private String dateFirstEvent;
	private String strBadge;
	private String strPoster;
}

/*"idLeague": "4328",
"strLeague": "English Premier League",
"strLeagueAlternate": "Premier League, EPL",
"strCurrentSeason": "2025-2026",
"intFormedYear": "1992",
"dateFirstEvent": "1992-08-15"
"strBadge": "https://r2.thesportsdb.com/images/media/league/badge/gasy9d1737743125.png",
"strPoster": "https://r2.thesportsdb.com/images/media/league/poster/67al0l1719007596.jpg",*/



