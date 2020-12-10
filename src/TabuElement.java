public class TabuElement {

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

    public TabuElement(int [] route, int kadencja){

        this.route = route;
        this.kadencja = kadencja;

    }

    public TabuElement(){

    }

}
