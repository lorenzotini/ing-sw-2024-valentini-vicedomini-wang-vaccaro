package it.polimi.ingsw.gc27;

import it.polimi.ingsw.gc27.Model.Card.*;
import it.polimi.ingsw.gc27.Model.Card.ObjectiveCard.*;
import it.polimi.ingsw.gc27.Model.Enumerations.CornerSymbol;
import it.polimi.ingsw.gc27.Model.Enumerations.Kingdom;
import it.polimi.ingsw.gc27.Model.Enumerations.PointsMultiplier;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class JsonParser{
    public static HashMap<String, Kingdom> kingdomHashMap = new HashMap<>();
    static {
        kingdomHashMap.put("PLANTKINGDOM", Kingdom.PLANTKINGDOM);
        kingdomHashMap.put("ANIMALKINGDOM", Kingdom.ANIMALKINGDOM);
        kingdomHashMap.put("INSECTKINGDOM", Kingdom.INSECTKINGDOM);
        kingdomHashMap.put("FUNGIKINGDOM", Kingdom.FUNGIKINGDOM);
        kingdomHashMap.put("EMPTY", Kingdom.EMPTY);
    }
    public static HashMap<String, CornerSymbol> cornerSymbolHashMap = new HashMap<>();
    static {
        cornerSymbolHashMap.put("PLANTKINGDOM", CornerSymbol.PLANTKINGDOM);
        cornerSymbolHashMap.put("ANIMALKINGDOM", CornerSymbol.ANIMALKINGDOM);
        cornerSymbolHashMap.put("INSECTKINGDOM", CornerSymbol.INSECTKINGDOM);
        cornerSymbolHashMap.put("FUNGIKINGDOM", CornerSymbol.FUNGIKINGDOM);
        cornerSymbolHashMap.put("QUILL", CornerSymbol.QUILL);
        cornerSymbolHashMap.put("INKWELL", CornerSymbol.INKWELL);
        cornerSymbolHashMap.put("MANUSCRIPT", CornerSymbol.MANUSCRIPT);
        cornerSymbolHashMap.put("EMPTY", CornerSymbol.EMPTY);
    }
    public static HashMap<String, PointsMultiplier> pointsMultiplierHashMapHashMap = new HashMap<>();
    static {
        pointsMultiplierHashMapHashMap.put("CORNER", PointsMultiplier.CORNER);
        pointsMultiplierHashMapHashMap.put("QUILL", PointsMultiplier.QUILL);
        pointsMultiplierHashMapHashMap.put("INKWELL", PointsMultiplier.INKWELL);
        pointsMultiplierHashMapHashMap.put("MANUSCRIPT", PointsMultiplier.MANUSCRIPT);
        pointsMultiplierHashMapHashMap.put("EMPTY", PointsMultiplier.EMPTY);
    }
    public static final ArrayList<String> XX = new ArrayList<>(Arrays.asList("UR", "UL", "LR", "LL"));
    public static final String filePath = "src/main/resources/Json/codex_cards_collection.json";
    public static JSONParser jsonParser = new JSONParser();
    public static JSONObject cardsJsonObj;

    static {
        try {
            cardsJsonObj = (JSONObject) jsonParser.parse(new FileReader(filePath));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<ResourceCard> getResourceDeck(JSONObject cardsJsonObj){

        ArrayList<ResourceCard> myDeck = new ArrayList<>();
        JSONArray jsonDeck = (JSONArray) cardsJsonObj.get("resourceDeck");

        //retrieve every card
        for(Object obj : jsonDeck){
            JSONObject jsonObject = (JSONObject) obj;
            int id = ((Long) jsonObject.get("id")).intValue();
            int cardPoints = ((Long) jsonObject.get("cardPoints")).intValue();
            Kingdom colour = kingdomHashMap.get((String) jsonObject.get("colour"));

            //FRONT
            JSONObject f = (JSONObject) jsonObject.get("frontFace");
            ArrayList<Corner> frontCorners = new ArrayList<>();
            for(String xx : XX){
                JSONObject cornerXX = (JSONObject) f.get("corner" + xx);
                boolean black = (boolean) cornerXX.get("black");
                CornerSymbol symbol = cornerSymbolHashMap.get((String) cornerXX.get("symbol"));
                Corner c = new Corner(black, symbol);
                frontCorners.add(c);
            }
            FrontFace front = new FrontFace(colour, frontCorners.get(0), frontCorners.get(1), frontCorners.get(2), frontCorners.get(3));

            //BACK
            ArrayList<Kingdom> permanentResources = new ArrayList<>();
            permanentResources.add(colour);
            Corner c1 = new Corner(false, CornerSymbol.EMPTY);
            Corner c2 = new Corner(false, CornerSymbol.EMPTY);
            Corner c3 = new Corner(false, CornerSymbol.EMPTY);
            Corner c4 = new Corner(false, CornerSymbol.EMPTY);
            BackFace back = new BackFace(colour, c1, c2, c3, c4, permanentResources);

            //modificare costruttore
            ResourceCard newCard = new ResourceCard(id, cardPoints, front, back);
            myDeck.add(newCard);
        }
        return myDeck;
    }
    public static ArrayList<GoldCard> getGoldDeck(JSONObject cardsJsonObj){
        ArrayList<GoldCard> myDeck = new ArrayList<>();
        JSONArray jsonDeck = (JSONArray) cardsJsonObj.get("goldDeck");

        //retrieve every card
        for(Object obj : jsonDeck){
            JSONObject jsonObject = (JSONObject) obj;
            int id = ((Long) jsonObject.get("id")).intValue();
            int cardPoints = ((Long) jsonObject.get("cardPoints")).intValue();
            Kingdom colour = kingdomHashMap.get((String) jsonObject.get("colour"));

            ArrayList<String> tmp = (ArrayList<String>) jsonObject.get("requirements");
            ArrayList<Kingdom> requirements = new ArrayList<>();
            requirements = tmp.stream().map(kingdomHashMap::get).collect(Collectors.toCollection(ArrayList::new));
            PointsMultiplier pointsMultiplier = pointsMultiplierHashMapHashMap.get((String) jsonObject.get("pointsMultiplier"));

            //FRONT
            JSONObject f = (JSONObject) jsonObject.get("frontFace");
            ArrayList<Corner> frontCorners = new ArrayList<>();
            for(String xx : XX){
                JSONObject cornerXX = (JSONObject) f.get("corner" + xx);
                boolean black = (boolean) cornerXX.get("black");
                CornerSymbol symbol = cornerSymbolHashMap.get((String) cornerXX.get("symbol"));
                Corner c = new Corner(black, symbol);
                frontCorners.add(c);
            }
            FrontFace front = new FrontFace(colour, frontCorners.get(0), frontCorners.get(1), frontCorners.get(2), frontCorners.get(3));

            //BACK
            ArrayList<Kingdom> permanentResources = new ArrayList<>();
            permanentResources.add(colour);
            Corner c1 = new Corner(false, CornerSymbol.EMPTY);
            Corner c2 = new Corner(false, CornerSymbol.EMPTY);
            Corner c3 = new Corner(false, CornerSymbol.EMPTY);
            Corner c4 = new Corner(false, CornerSymbol.EMPTY);
            BackFace back = new BackFace(colour, c1, c2, c3, c4, permanentResources);

            //modificare costruttore
            GoldCard newCard = new GoldCard(id, cardPoints, front, back, requirements, pointsMultiplier);
            myDeck.add(newCard);
        }
        return myDeck;
    }

    //ATTENZIONE: PER LE STARTER, FRONT E BACK SONO INVERTITI NELLE RISORSE GRAFICHE. FARE AFFIDAMENTO AL LIBRETTO DELLE REGOLE
    public static ArrayList<StarterCard> getStarterDeck(JSONObject cardsJsonObj){
        ArrayList<StarterCard> myDeck = new ArrayList<>();
        JSONArray jsonDeck = (JSONArray) cardsJsonObj.get("starterDeck");

        //retrieve every card
        for(Object obj : jsonDeck){
            JSONObject jsonObject = (JSONObject) obj;

            int id = ((Long) jsonObject.get("id")).intValue();
            Kingdom colour = Kingdom.EMPTY;

            //FRONT
            JSONObject f = (JSONObject) jsonObject.get("frontFace");
            ArrayList<Corner> frontCorners = new ArrayList<>();
            for(String xx : XX){
                JSONObject cornerXX = (JSONObject) f.get("corner" + xx);
                CornerSymbol symbol = cornerSymbolHashMap.get((String) cornerXX.get("symbol"));
                Corner c = new Corner(false, symbol);
                frontCorners.add(c);
            }
            FrontFace front = new FrontFace(colour, frontCorners.get(0), frontCorners.get(1), frontCorners.get(2), frontCorners.get(3));

            //BACK
            JSONObject b = (JSONObject) jsonObject.get("backFace");
            ArrayList<Corner> backCorners = new ArrayList<>();
            ArrayList<String> tmp = (ArrayList<String>) jsonObject.get("permanentResources");
            ArrayList<Kingdom> permanentResources = new ArrayList<>();
            permanentResources = tmp.stream().map(kingdomHashMap::get).collect(Collectors.toCollection(ArrayList::new));
            for(String xx : XX){
                JSONObject cornerXX = (JSONObject) b.get("corner" + xx);
                boolean black = (boolean) cornerXX.get("black");
                CornerSymbol symbol = cornerSymbolHashMap.get((String) cornerXX.get("symbol"));
                Corner c = new Corner(black, symbol);
                backCorners.add(c);
            }
            BackFace back = new BackFace(colour, backCorners.get(0), backCorners.get(1), backCorners.get(2), backCorners.get(3), permanentResources);

            //modificare costruttore
            StarterCard newCard = new StarterCard(id, front, back);
            myDeck.add(newCard);
        }
        return myDeck;
    }
    public static ArrayList<ObjectiveCard> getObjectiveDeck(JSONObject cardsJsonObj){
        ArrayList<ObjectiveCard> myDeck = new ArrayList<>();
        JSONArray jsonDeck = (JSONArray) cardsJsonObj.get("objectiveDeck");

        Kingdom colour = Kingdom.EMPTY;
        Corner c = new Corner(true, CornerSymbol.EMPTY);

        //retrieve every card
        for(Object obj : jsonDeck) {
            JSONObject jsonObject = (JSONObject) obj;
            int id = ((Long) jsonObject.get("id")).intValue();
            FrontFace front = new FrontFace(colour, c, c, c, c);
            BackFace back = new BackFace(colour, c, c, c, c, null);

            ObjectiveCard newCard = null;
            if (id >= 87 && id <= 90) {
                Kingdom ladderKingdom = kingdomHashMap.get((String) jsonObject.get("ladderKingdom"));
                boolean upscaling = (boolean) jsonObject.get("upscaling");
                newCard = new LadderPattern(id, front, back, ladderKingdom, upscaling);

            } else if (id >= 91 && id <= 94) {
                Kingdom firstColour = kingdomHashMap.get((String) jsonObject.get("firstColour"));
                Kingdom secondColour = kingdomHashMap.get((String) jsonObject.get("secondColour"));
                int i = ((Long) jsonObject.get("i")).intValue();
                int j = ((Long) jsonObject.get("j")).intValue();
                newCard = new TwoPlusOnePattern(id, front, back, firstColour, secondColour, i, j);

            } else if (id >= 95 && id <= 98) {
                Kingdom kingdom = kingdomHashMap.get((String) jsonObject.get("kingdom"));
                newCard = new ThreeKingdomPattern(id, front, back, kingdom);

            } else if (id == 99) {
                newCard = new DifferentPattern(id, front, back);

            } else if (id >= 100 && id <= 102) {
                CornerSymbol cornerSymbol = cornerSymbolHashMap.get((String) jsonObject.get("cornerSymbol"));
                newCard = new DoublePattern(id, front, back, cornerSymbol);

            }
            myDeck.add(newCard);
        }
        return myDeck;
    }

}


//ArrayList<ResourceCard> resourceDeck = JsonParser.getResourceDeck(JsonParser.cardsJsonObj);
//ArrayList<GoldCard> goldDeck = JsonParser.getGoldDeck(JsonParser.cardsJsonObj);
//ArrayList<StarterCard> starterDeck = JsonParser.getStarterDeck(JsonParser.cardsJsonObj);
//ArrayList<ObjectiveCard> objectiveDeck = JsonParser.getObjectiveDeck(JsonParser.cardsJsonObj);