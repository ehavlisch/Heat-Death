package main;

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
		"Lance", 
		"Maury", 
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
		"bo", "ba", 
		"co", "ca", 
		"do", "da", 
		"-eo", "'e", 
		"fo", "fa", 
		"go", "ga", 
		"ho", "ha", 
		"ia", 
		"ja", 
		"ku", "-kenobi", 
		"loa", 
		"mu", 
		"nu", 
		"oop", 
		"po", 
		"'qoc", 
		"ro", 
		"sa", 
		"to", 
		"ua", 
		"vo", 
		"wan", 
		"'xix", 
		"ya", 
		"zu", 
		" I", " II", " III", " IV", " V", " VI", " VII", " VIII", " IX", " X", " XI", " XII", " XIII", " XIV", " XV", " MMXIII", " MXII", " LXII", " LXI", " DCLVI", 
		" One", " Two", " Three", " Fwe", " Four", " Five", " Six", " Seven", " Eight", " Nine", " Ten"

	};
}
