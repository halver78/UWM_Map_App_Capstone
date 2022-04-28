package com.example.uwmmapapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Coordinates {
    public static Map<String,double[]> coordList;

    public static void init(){

        coordList = new HashMap<String,double[]>();
        double[] unionCoord = {43.075144698206685,-87.88242486618464};
        double[] mellCoord = {43.075294568299284, -87.87981831571807};
        double[] mitchCoord = {43.075610954500405, -87.87929101756576};
        double[] purinCoord = {43.07501854906647, -87.8776627022242};
        double[] zelazoCoord = {43.07488451518848, -87.8796601539664};
        double[] spaightsCoord = {43.075833015421466, -87.88080339232128};
        double[] musicbCoord = {43.07643247587727, -87.8796946600398};
        double[] theatrebCoord = {43.07591610832341, -87.87959703728356};
        double[] boltonCoord = {43.07603262712223, -87.88127393094315};
        double[] lubarCoord = {43.07617770554442, -87.88226630210704};
        double[] gmLibraryCoord = {43.07710550769342, -87.88053837327082};
        double[] curtinCoord = {43.076632641353186, -87.87858187327083};
        double[] vogelCoord = {43.077061207852026, -87.87838954443474};
        double[] pearseCoord = {43.07724066029498, -87.87851818676243};
        double[] garlandCoord = {43.07728304378343, -87.87906381745148};
        double[] johnstonCoord = {43.07828422049913, -87.8781077867624};
        double[] enderisCoord  = {43.07823144026581, -87.87983431745145};
        double[] holtonCoord = {43.078494629580334, -87.87910370210696};
        double[] merrillCoord = {43.07847145431667, -87.87854290210687};
        double[] greeneCoord = {43.07606596390058, -87.88425220951879};
        double[] norrisCoord = {43.07853651008192, -87.8794960155985};
        double[] sabinCoord = {43.07970074351247, -87.87849370210687};
        double[] pavillionCoord = {43.08047407774595, -87.87962585977913};
        double[] groundsCoord = {43.08122361185241, -87.87940311745139};
        double[] downerWoodsCoord = {43.07845889312698, -87.88200078861534};
        double[] sandburgCoord = {43.07917025437202, -87.8822353444346};
        double[] chapmanCoord = {43.07812521829033, -87.88103690210694};
        double[] honorsCoord = {43.07922004480347, -87.8834665597792};
        double[] northQACoord = {43.078246191278765, -87.88347077512375};
        double[] northQBCoord = {43.078250974219436, -87.88429361559855};
        double[] northQCCoord = {43.078250974219436, -87.88429361559855};
        double[] northQDCoord = {43.078250974219436, -87.88429361559855};
        double[] northQECoord = {43.07878829030806, -87.88319950210693};
        double[] cunninghamCoord = {43.0775448960494, -87.88575191745149};
        double[] engelmannCoord = {43.07753435939356, -87.8842364444347};
        double[] archiCoord = {43.07710595048162, -87.88320250210694};
        double[] chemistryCoord = {43.07617041613415, -87.884929402107};
        double[] emsCoord = {43.07588501409983, -87.88577940588156};
        double[] laphamCoord = {43.075851769356326, -87.88407668861545};
        double[] kenwoodrCoord = {43.07562965444644, -87.88389410210704};
        double[] physicsCoord = {43.075278970528394, -87.88606264443476};
        double[] lubareCoord = {43.075103103856456, -87.88381384628768};
        double[] hefterCoord = {43.07730656637946, -87.87295003094307};
        double[] usrCoord = {43.091922301626894, -87.91023835977875};
        double[] riverviewCoord = {43.06070380034849, -87.89523468676298};
        double[] cambridgeCoord = {43.06087239407427, -87.8918521002546};
        double[] kenilworthCoord = {43.0591189303646, -87.88593412909077};
        double[] kenilwortheCoord = {43.05855668295813, -87.88580580210761};
        double[] zilberCoord = {43.0468570286708, -87.92422320210802};
        double[] continECoord = {43.03847762915711, -87.91218561745286};
        double[] globalwCoord = {43.0293191502236, -87.91373937512545};
        double[] freshwaterCoord = {43.01721237914829, -87.90399133650399};

        coordList.put("UWM Student Union*", unionCoord);
        coordList.put("Mellencamp Hall", mellCoord);
        coordList.put("Mitchell Hall", mitchCoord);
        coordList.put("Purin Hall", purinCoord);
        coordList.put("Zelazo Center for the Performing Arts", zelazoCoord);
        coordList.put("Spaights Plaza", spaightsCoord);
        coordList.put("Music Building", musicbCoord);
        coordList.put("Theater Building", theatrebCoord);
        coordList.put("Bolton Hall*", boltonCoord);
        coordList.put("Lubar Hall*", lubarCoord);
        coordList.put("Golda Meir Library", gmLibraryCoord);
        coordList.put("Curtin Hall", curtinCoord);
        coordList.put("Vogel Hall", vogelCoord);
        coordList.put("Pearse Hall", pearseCoord);
        coordList.put("Garland Hall", garlandCoord);
        coordList.put("Johnston Hall", johnstonCoord);
        coordList.put("Enderis Hall", enderisCoord);
        coordList.put("Holton Hall", holtonCoord);
        coordList.put("Merrill Hall", merrillCoord);
        coordList.put("Greene Hall", greeneCoord);
        coordList.put("Norris Health Center", norrisCoord);
        coordList.put("Sabin Hall", sabinCoord);
        coordList.put("Pavillion", pavillionCoord);
        coordList.put("Grounds Building", groundsCoord);
        coordList.put("Downer Woods", downerWoodsCoord);
        coordList.put("Sandburg Residence Hall West Tower", sandburgCoord);
        coordList.put("Chapman Hall", chapmanCoord);
        coordList.put("Honors House", honorsCoord);
        coordList.put("Northwest Quadrant Building A", northQACoord);
        coordList.put("Northwest Quadrant Building B", northQBCoord);
        coordList.put("Northwest Quadrant Building C", northQCCoord);
        coordList.put("Northwest Quadrant Building D", northQDCoord);
        coordList.put("Northwest Quadrant Building E", northQECoord);
        coordList.put("Cunningham Hall", cunninghamCoord);
        coordList.put("Engelmann Hall", engelmannCoord);
        coordList.put("Architecture & Urban Planning Bldg", archiCoord);
        coordList.put("Chemistry Building*", chemistryCoord);
        coordList.put("Engineering and Math. Science Building*", emsCoord);
        coordList.put("Lapham Hall", laphamCoord);
        coordList.put("Kenwood Interdisciplinary Research Complex", kenwoodrCoord);
        coordList.put("Physics Building", physicsCoord);
        coordList.put("Lubar Entrepreneurship Center & UWM Welcome Center", lubareCoord);
        coordList.put("Hefter Conference Center", hefterCoord);
        coordList.put("(USR) University Services and Research Building", usrCoord);
        coordList.put("RiverView Residence Hall", riverviewCoord);
        coordList.put("Cambridge Commons", cambridgeCoord);
        coordList.put("Kenilworth Square Apartments", kenilworthCoord);
        coordList.put("Kenilworth Square East", kenilwortheCoord);
        coordList.put("Zilber School of Public Health Building", zilberCoord);
        coordList.put("Continuing Education Plankinton Building", continECoord);
        coordList.put("Global Water Center", globalwCoord);
        coordList.put("School of Freshwater Sciences", freshwaterCoord);
    }
    public static Map<String,double[]> getcoords(){
        return coordList;
    }


}
