package co.workamerica.entities.statistics;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "WeeklyStats",  schema = "workamericadb")
public class WeeklyStats implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="WeeklyStatsID")
	private int weeklyStatsID;
	@Column(name="TotalProfiles")
	private int totalProfiles;
	@Column(name = "TotalCompleteProfiles")
    private int totalCompleteProfiles;
    @Column(name="Date")
	private String date;
	
	public WeeklyStats() {
	
	}

	public int getWeeklyStatsID() {
		return weeklyStatsID;
	}

	public void setWeeklyStatsID(int weeklyStatsID) {
		this.weeklyStatsID = weeklyStatsID;
	}

	public int getTotalProfiles() {
		return totalProfiles;
	}

	public void setTotalProfiles(int totalProfiles) {
		this.totalProfiles = totalProfiles;
	}

    public int getTotalCompleteProfiles() {
        return totalCompleteProfiles;
    }

    public void setTotalCompleteProfiles(int totalCompleteProfiles) {
        this.totalCompleteProfiles = totalCompleteProfiles;
    }

	public String getDate() {
		return date != null ? date : "";
	}

	public void setDate(String date) {
		this.date = date;
	}
}
