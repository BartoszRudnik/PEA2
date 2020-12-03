public class TabuList {

    private int [] route;
    private int kadencja;

    public int[] getRoute() {
        return route;
    }

    public void setRoute(int[] route) {
        this.route = route;
    }

    public int getKadencja() {
        return kadencja;
    }

    public void setKadencja(int kadencja) {
        this.kadencja = kadencja;
    }

    public TabuList(int [] route, int kadencja){

        this.route = route;
        this.kadencja = kadencja;

    }

    public TabuList(){

    }

}
