package main;

import childrenLocations.Planet;

public class Randomizer extends RNG {

	public static String randomName() {
		String name = "";

		int random = random(100);

		if (random > 1) {
			name += prefix[random(prefix.length)];
		}

		name += body[random(body.length)];

		random = random(100);
		if (random > 1) {
			name += " " + body[random(body.length)];
		}

		random = random(100);
		if (random > 1) {
			name += suffix[random(suffix.length)];
		}

		return name;
	}

	public static String randomNameNumber() {
		String name = "";

		int random = random(100);

		if (random > 50) {
			name += prefix[random(prefix.length)];
		}

		name += body[random(Math.abs(body.length))];

		random = random(100);
		if (random > 50) {
			name += suffix[random(suffix.length)];
		}
		if (random > 10) {
			name += "-" + random(999);
		}

		return name;
	}
	
	public static void randomPlanet(Planet p) {
		int size = random(990);
		StringBuilder detailed = new StringBuilder("");
		if(size < 3) {
			p.setDesc("A mercurian planet.");
			detailed.append(lowMassPlanetDetail[random(lowMassPlanetDetail.length)]);
			detailed.append(" \n");
			detailed.append(lowMassPlanetAtmosphere[random(lowMassPlanetAtmosphere.length)]);
			detailed.append(" \n");
			p.setDetailed(detailed.toString());
		} else if(size < 10) {
			p.setDesc("A subterran planet.");
			detailed.append(lowMassPlanetDetail[random(lowMassPlanetDetail.length)]);
			detailed.append(" \n");
			detailed.append(lowMassPlanetAtmosphere[random(lowMassPlanetAtmosphere.length)]);
			detailed.append(" \n");
			p.setDetailed(detailed.toString());
		} else if(size < 21) {
			p.setDesc("A terran planet.");
			detailed.append(medMassPlanetDetail[random(medMassPlanetDetail.length)]);
			detailed.append(" \n");
			detailed.append(medMassPlanetAtmosphere[random(medMassPlanetAtmosphere.length)]);
			detailed.append(" \n");
			p.setDetailed(detailed.toString());
		} else if(size < 131) {
			p.setDesc("A superterran planet.");
			detailed.append(medMassPlanetDetail[random(medMassPlanetDetail.length)]);
			detailed.append(" \n");
			detailed.append(medMassPlanetAtmosphere[random(medMassPlanetAtmosphere.length)]);
			detailed.append(" \n");
			p.setDetailed(detailed.toString());
		} else if(size < 279) {
			p.setDesc("A neptunian planet.");
			detailed.append(medMassPlanetDetail[random(medMassPlanetDetail.length)]);
			detailed.append(" \n");
			detailed.append(medMassPlanetAtmosphere[random(medMassPlanetAtmosphere.length)]);
			detailed.append(" \n");
			p.setDetailed(detailed.toString());
		} else if(size < 980) {
			p.setDesc("A jovian planet.");
			detailed.append(highMassPlanetDetail[random(highMassPlanetDetail.length)]);
			detailed.append(" \n");
			detailed.append(highMassPlanetAtmosphere[random(highMassPlanetAtmosphere.length)]);
			detailed.append(" \n");
			p.setDetailed(detailed.toString());
		} else {
			p.setDesc("A planet that should be classified as a low-mass brown dwarf.");
			detailed.append(highMassPlanetDetail[random(highMassPlanetDetail.length)]);
			detailed.append(" \n");
			detailed.append(highMassPlanetAtmosphere[random(highMassPlanetAtmosphere.length)]);
			detailed.append(" \n");
			p.setDetailed(detailed.toString());
		}
	}

	private static String[] prefix = { 
		"A-", "Anna", "Aw", 
		"Bo", "Ba", "Bi", 
		"Ca", "Co", "Cae-", 
		"Dae", "De'", "Do'", 
		"E'", "Ev", "E-",
		"Fa", "Fae", "Fu", 
		"Gor", "Gen",
		"Hu", "He", "Ha", "Hi",
		"Indigo ", "In", "Ira", "I'a",
		"Jango ", "J'",
		"King ", "Ko", "Knave ", 
		"Leroy ", "Le ",
		"Moon ", "Mo'",
		"Nok", "Nu",
		"Oppa", "Oph",
		"Po", "Pent",
		"Quin ", "Qu'",
		"Riley ", "Ravager ",
		"Sasha ", "Sir ",
		"Thaka", "Thane ",
		"Urvashi ", "Uru ",
		"Vinny 'Slim' ", 
		"Wo", "Wonderful ",
		"Xana-", "X-", 
		"Yuri ", 
		"Zant "};

	private static String[] body = { 
		"Aanaa", "Alpha", "Ankh", "Aoraka", "Abb", "Avaline", "Ava", "Ama", "Amy", "Amio", "Azeem", "Azriel", "Akim", "Akoo", "Aku", "Abra", "Abba", "Abalon", "Avalon", "Avon", "Avin", "Aon", "Aion", "Aether", "Aebor", "Akor", "Akin", "Avin", "Avid", "Akron", "Auora", "Arora", "Aoki", "Abu", "Agro", "Agora", "Aggoron", "Aggron", "Ak'Ack", 
		"Ba", "Bant", "Bravo", "Bane", "Boon", "Bronner", "Bromley", "Breen", "Bree", "Boop", "Bwee", "Bool", "Bovin", 
		"Col", "Charlie", "Cat", "Cang", "Cok", "Colt", "Corgie", "Coke", "Cooler", "Crytek", "Century", "Centarius", "Centan", "Cretan", "Cerbin", "Creed", "Cantor", "Cant", "Carten", "Caravel", "Cilo", "Cillo", "Cint", "Country", "Contreras", "Contrera", "Contra", "Controlla", "Contriven", "Cag", "Cong", "Corp", 
		"Dea", "Dean", "Delta", "Dole", "Dick", "Do'urden", "Drizzt", "Draven", "Dragath", "Delta-Delta", "Donner", "Doogan", "Dugan", "Dogan", "Diggin", "Dugtrian", "Dugtrius", "Didgeridoo", "Delphus", "Dolphus", "Dilphos", "Dilophos", "Dilamphus", "Dilaphos", "Diko", "Dickens", "Discree", 
		"Evan", "Echo", "Endicia", "Eberron", "Enchor", "Echelon", "Eve", "Ekans", "Esper", "Elron", "Edgar", "Ed", "Edd", "Eddy", "Edward", "Eva", "Evangeline", "Epsilon", "Ephoria", "Eru", "Eros", "Ewan", "Ewok", "Eon", 
		"Fang", "Foxtrot", "Foltest", "Forestia", "Fortrest", "Fortress", "Flotilla", "Flotzul", "Flotsam", "Flotjam", "Flotpam", "Flutenwaffe", "Flutenwiff", "Flutterwuff", "Fi", "Fee", "Fo", "Fum", "Foldun", "Foldus", "Forpal", "Fequel", "Ferrous", "Ferrut", "Ferrin", "Ferious", "Furious", "Festus", 
		"Gorilla", "Golf", "Gellan", "Gelax", "Gerean", "Geen", "Gerrus", "Garrus", "Gentile", "Gentul", "Geodude", "Georock", "Geodurf", "Ganondorf", "Ganon", "Ganoon", "Ganymede", "Ganymed", "Gainera", "Gaint", "Golus", "Golem", "Gollum", "Gollux", "Gyrux", "Gyrus", "Gim", "Givree", "Givve", "Gewd", "Gyre", "Gestalt", "Gestuate", "Gestire", "Gonull", "Griffin",
		"Human", "Hotel", "Harrus", "Holtest", "Hansolo", "Haan", "Ha'an", "Haolo", "Hawatha", "Hawaii", "Hallow", "Hallula", "Habuwan", "Hell", "Heaven", "Haven", "Harlot", "Heavol", "Hewlet", "Hewitt", "Hinder", "Hinda", "Hilda", "Hilldar", "Holbarth", "Hobarth", "Hobbit", "Hogan", "Horvath", "Horowitz", "Herowiz", "Hubile", "Heuris", "Heureel", "Huw-Huw", "Huwen", "Hywalla", "Hyrule", "Hyness", "Hyphy", 
		"Ivy", "Indigo", "Ichor", "Ike", "Izzy", "Izzit", "Ira", "Io", "Ian", "Iava", "Iwan", "Itin", "Ivan", "Ivanov", "Itska", "Itchy", "Idra", "Ivy", "Iberron", "Illoria", "Ibo", 
		"Jenkins", "Jam", "Jaspa", "Jasper", "Jank", "Jav", "Jay", "Jackson", "James", "Jac", "Jaque", "Jacue", "Jessie", "Jess", "Jessica", "Jelila", "Jel", "Jen", "Jei", "Jeb", "Jill", "Jiggy", "Jityr", "Jio", "Joline", "Jolina", "Joana", "Joan", "Jostalin", "Jost", "Justin", "Jurt", "Jurgens", "Juren", "Jurl", "Jurlaya", 
		"Kongor", "Kang", "Kasper", "Karl", "Kaw", "Kav", "Kaven", "Ken", "Kent", "Kelly", "Kellen", "Kelins", "Kebbo", "Kebbin", "Kerbal", "Kinesia", "Kiness", "Kibble", "Krill", "Kabogan", "Krean", "Korea", "Kyoto", "Kyung", "King", "Knight", "Kool", "Kruel", "Krux", "Knox", "Knoxwell", "Kvorkian",
		"Lance", "Lawrence", "Laren", "Larson", "Lanius", "Legate", "Legault", "Legolas", "Legion", "Lego", "Lee", "Lester", "Leven", "Levan", "Light", "Li", "Liral", "Lira", "Lici", "Lob", "Loki", "Looti", "Logan", "Lou", "Louise", "Lana", "Lam", "Lauralei", "Lefty", "Lingo", "Lop",
		"Maury", "Maurice", "Maggie", "Mags", "Mat", "Matthew", "Matterhorn", "Marius", "Megan", "Meegan", "Megara", "Meshys", "Mesh", "Memnon", "Memoran", "Memory", "Missus", "Mistress", "Misty", "Maggie", "Mitch", "Mitchel", "Mitt", "Mutany", "Muthu", "Marbo", "Marb", "Musk", "Mow", "Mao", "Mu", "Mofat", "Mupen", "Moxy", "Moxie", "Moot",
		"Nat", "Nature", "Ngata", "Nata", "Noo", "Noala", "Nean", "Neon", "Nequan", "Nava", "Nala", "Naan", "Na'an", "Nulia", "Nuzlef", "Ninnela", "Ni", "Night", "Nod", "Nodus", "Nodule", "Nobilus", "Nobintus", "Nabintan", "Nab-Nab", "Ninte", "Ninter", "Ning", "Nool", 
		"Oogie", "Oana", "Oak", "Oave", "Obby", "Obsidian", "Obrella", "Obediah", "Occan", "Ocphen", "Oden", "Odin", "Odo", "Ody", "Oen", "Oflen", "Ogden", "Ogdon", "Ogren", "Ohvie", "Ohven", "Oit", "Oj", "Ojama", "Ojena", "Ophelia", "Oaken", "Oken", "Olaf", "Olen", "Ola", "Omon", "Omonie", "Onota", "Onis", "Onus", "Oogie", "Ooten", "Oppenheimer", "Opp", "Oqua", "Orphus", "Orus", "Oru", "Ora", "Oren", "Orilla", "O'Riley", "O'Roulee",
		"Polly", 
		"Quick ", 
		"Rails", "Riley", "Ridley", "Riven", "Raven", "Rishuu", "Riana", "Ribley", "Ripley", "Randy", "Ra", "Rabin", "Reddy", "Rekt", "Relvin", "Reffin", "Regie", "Rinso", "Rincon", "Robin", "Robby", "Rob", "Rolaf", "Relf", "Ryan", "Rhrist", "Radagast", 
		"Slide", "Solax", "Solar", "Sven", "Sant", "Santu", "Sana", "Sango", "Sora", "Sara", "Saren", "Suri", "Sikash", "Sur", "Swolk", "Syin", "Syon", "Streb", 
		"Toa", "Tovin", "Tobin", "Tabin", "Tavin", "Tralfalmadore", "Teana", "Teek", "Tsara", "Tsar", "Tzar", "Twilek", "Twill", "Thread", "Twitter", "Twobble", "Twibble", "Twin", "TwoTwo", "Tear", "TeBora", "Tymora", "TeNora", "TeVora", "Tupac", "Tubula", "Tabula", "Tibula", "Tion", "Tiburon", "Tubor", "Tubina", "Tube", "Tog", "Togor", "Tongor", "Tonga", "Tahi", "Tajiti", "Tofer", "Tufera", "Tuxana", "Tuxor", "Tupur", "Tupier", 
		"Uri", "Uriah", "Urbano", "Uran", "Ucco", "Uquan", "Uwalla", "Urza", "Urion", "Ute", "Upona", "Upina", "Ustora", "Usgat", "Udda", "Uft", "Ugga", "Ug-Ooh", "Ujmala", "Ujimin", "Ukelele", "Ulamog", "Uztra", "Uxhil", "Uve", "Uber", "Unit", "Unee", "Unoa", "Umber", 
		"Vane", 
		"Wall", "Wane", "Waipo", "Waipu", "Waka", "Wana", "Wells", "Wesley", "Wein", "Ween", "Webble", "Weeble", "Weant", "Wew", "Winter", "Wilpo", "Wilco", "Witten", "Wire", "Wine", "Winkey", "Woon", "Wonna", "Wolla", "Wolpin", "Wott", "Wov", "Wovin", "Wunderlass", "Wunderbal", "Wunner", "Wu'un", "Wrynn",
		"Xatan", "X", 
		"Yatan", 
		"Zalvo" };

	private static String[] suffix = { 
		"'a", "-a", "-aa", "al Assan", 
		"bo", "ba", "bi", "be",
		"co", "ca", "can",
		"do", "da", "dan",
		"-eo", "'e", "ean",
		"fo", "fa", 
		"go", "ga", 
		"ho", "ha", 
		"ia", "iba",
		"ja", "jojo",
		"ku", "-kenobi", 
		"loa", "lo",
		"mu",  "ma",
		"nu", "na",
		"oop", "o",
		"po", "pa",
		"'qoc", "-qua",
		"ro", "raro",
		"sa", "san",
		"to", "tan",
		"ua", "u",
		"vo", "vox",
		"wan", "wa",
		"'xix", "xiu",
		"ya", "yin",
		"zu", "zan",
		" I", " II", " III", " IV", " V", " VI", " VII", " VIII", " IX", " X", " XI", " XII", " XIII", " XIV", " XV", " MMXIII", " MXII", " LXII", " LXI", " DCLVI", 
		" One", " Two", " Three", " Fwe", " Four", " Five", " Six", " Seven", " Eight", " Nine", " Ten"
	};

	
	private static String[] lowMassPlanetDetail = {
		"A tiny rock floating in space.",
		"A small lonely planet lost in space.",
		"Top 3 smallest planets I have ever seen.",
		"A small planet maybe .5 Old Earth Masses.",
		"A small planet pocked with asteroid impacts.",
		"A tiny planet with a good chunk, probably an asteroid or bad pilot.",
		"A tiny planet with a large scorched piece missing, probably a weapons test. Need to test for radioactivity.",
		"A 'planet' formed from two large asteroids that stuck.",
		"A man made diamond planet. A natural phenomena or extravagant gift.",
		"A small ball of mostly iron."
	};
	
	private static String[] lowMassPlanetAtmosphere = {
		"Typical subterran atmosphere. Thin frozen clouds of ammonia.",
		"Very tenous and highy variable atmosphere containing hydrogen, helium, oxygen, and more.",
		"Only a thin exosphere, definately not enough to human life.",
		"Atmosphere contains mostly hydrogen. Very thin.",
		"No atmosphere, probably closer to an asteroid.",
		"No substantial atmosphere."
	};
	/*
	 *  hypopsychroplanets < -50 C
	 *  psychroplanets -50 - 0 C
	 *  mesoplanets 0 - 50 C
	 *  thermoplanets 50 - 100 C
	 *  hyperthermoplanets 100 + C
	 */
	private static String[] medMassPlanetDetail = {
		"An Old Earth-like planet that is uninhabited ... Highly radioactive.",
		"A mesoplanet with dense clouds blocking the surface.",
		"A decayed hypopsychroplanet that is showing large veins of ice.",
		"A once hyperthermoplanet, now mostly just iron and obsidian.",
		"A psychroplanet with small veins of iron and other useless.",
		"A hypopsychroplanet you can see the cold from here."
	};
	
	private static String[] medMassPlanetAtmosphere = {
		"The atmosphere is dominated by sulfur and other volcanic gasses.",
		"The atmosphere is rich in nitrogen and oxygen.",
		"The atmosphere is dense with heavy gases.",
		"Unable to get an atmosphere reading. Not sure why.",
		"The atmosphere contains a highly infectious terminal virus.",
		"The atmosphere is rich in nitrogen with clouds of ammonia.",
		"The atmosphere is rich in helium that will explode on reentry."
	};
	
	private static String[] highMassPlanetDetail = {
		"An ice giant with great swirling ice storms ravaging the frozen surface.",
		"A gas giant with blistering winds tearing deep valleys in the giant mountain chains.",
		"A very large planet that may be a low mass brown dwarf rather than an actual planet.",
		"A large planet with a thick cloud layer with remains of a civilization poking through.",
		"A gas giant surrounded by a dyson ring. Wonder what happened to this one...",
		"An ice giant that has grown so cold, the clouds have frozen solid like a giant marble.",
		"A gas super giant with cloud patterns in a polka dot pattern. Heavy pollution centers likely.",
		"A red and orange gas giant.",
		"A red gas giant.",
		"A blue and green gas giant.",
		"An engineered gas giant that once had a rainbow pattern, doesn't look as nice anymore.",
		"A gas giant with a lovevly vomit hue.",
		"An ice giant with lime green tint.",
		"An ice giant with an appropriate blue steel appearance."
	};
	
	private static String[] highMassPlanetAtmosphere = {
		"90% hydrogen 8% helium plus some other elements.",
		"The atmosphere is a harsh mix of methane and ammonia."
	};
}
