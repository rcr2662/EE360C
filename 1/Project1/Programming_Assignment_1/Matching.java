import java.util.ArrayList;

/**
 * A Matching represents a candidate solution to the stable matching problem. A Matching may or may
 * not be stable.
 */
public class Matching {
    /**
     * Number of cities.
     */
    private Integer m;

    /**
     * Number of doctors.
     */
    private Integer n;

    /**
     * A list containing each city's preference list.
     */
    private ArrayList<ArrayList<Integer>> city_preference;

    /**
     * A list containing each doctor's preference list.
     */
    private ArrayList<ArrayList<Integer>> doctor_preference;

    /**
     * Number of positions available in each city.
     */
    private ArrayList<Integer> city_positions;

    /**
     * Matching information representing the index of city a doctor is matched to, -1 if not
     * matched.
     *
     * <p>An empty matching is represented by a null value for this field.
     */
    private ArrayList<Integer> doctor_matching;

    public Matching(
            Integer m,
            Integer n,
            ArrayList<ArrayList<Integer>> city_preference,
            ArrayList<ArrayList<Integer>> doctor_preference,
            ArrayList<Integer> city_positions) {
        this.m = m;
        this.n = n;
        this.city_preference = city_preference;
        this.doctor_preference = doctor_preference;
        this.city_positions = city_positions;
        this.doctor_matching = null;
    }

    public Matching(
            Integer m,
            Integer n,
            ArrayList<ArrayList<Integer>> city_preference,
            ArrayList<ArrayList<Integer>> doctor_preference,
            ArrayList<Integer> city_positions,
            ArrayList<Integer> doctor_matching) {
        this.m = m;
        this.n = n;
        this.city_preference = city_preference;
        this.doctor_preference = doctor_preference;
        this.city_positions = city_positions;
        this.doctor_matching = doctor_matching;
    }

    /**
     * Constructs a solution to the stable matching problem, given the problem as a Matching. Take a
     * Matching which represents the problem data with no solution, and a doctor_matching which
     * solves the problem given in data.
     *
     * @param data              The given problem to solve.
     * @param doctor_matching The solution to the problem.
     */
    public Matching(Matching data, ArrayList<Integer> doctor_matching) {
        this(
                data.m,
                data.n,
                data.city_preference,
                data.doctor_preference,
                data.city_positions,
                doctor_matching);
    }

    /**
     * Creates a Matching from data which includes an empty solution.
     *
     * @param data The Matching containing the problem to solve.
     */
    public Matching(Matching data) {
        this(
                data.m,
                data.n,
                data.city_preference,
                data.doctor_preference,
                data.city_positions,
                new ArrayList<Integer>(0));
    }

    public void setDoctorMatching(ArrayList<Integer> doctor_matching) {
        this.doctor_matching = doctor_matching;
    }

    public Integer getCityCount() {
        return m;
    }

    public Integer getDoctorCount() {
        return n;
    }

    public ArrayList<ArrayList<Integer>> getCityPreference() {
        return city_preference;
    }

    public ArrayList<ArrayList<Integer>> getDoctorPreference() {
        return doctor_preference;
    }

    public ArrayList<Integer> getCityPositions() {
        return city_positions;
    }

    public ArrayList<Integer> getDoctorMatching() {
        return doctor_matching;
    }

    public int totalCityPositions() {
        int positions = 0;
        for (int i = 0; i < m; i++) {
            positions += city_positions.get(i);
        }
        return positions;
    }

    public String getInputSizeString() {
        return String.format("m=%d n=%d\n", m, n);
    }

    public String getSolutionString() {
        if (doctor_matching == null) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < doctor_matching.size(); i++) {
            String str = String.format("Doctor %d City %d", i, doctor_matching.get(i));
            s.append(str);
            if (i != doctor_matching.size() - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    public String toString() {
        return getInputSizeString() + getSolutionString();
    }
}