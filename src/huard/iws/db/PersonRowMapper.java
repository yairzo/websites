package huard.iws.db;

import huard.iws.model.Person;
import huard.iws.util.BaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PersonRowMapper implements ParameterizedRowMapper<Person> {
	//public ParameterizedRowMapper<Person> personRowMapper = new ParameterizedRowMapper<Person>(){
		public Person mapRow(ResultSet rs, int rowNum) throws SQLException{
            Person person = new Person();
            person.setId(rs.getInt("id"));
            person.setFirstNameHebrew(rs.getString("firstNameHebrew"));
            person.setLastNameHebrew(rs.getString("lastNameHebrew"));
            person.setDegreeHebrew(rs.getString("degreeHebrew"));
            person.setCivilId(rs.getString("civilId"));
            person.setFirstNameEnglish(rs.getString("firstNameEnglish"));
            person.setLastNameEnglish(rs.getString("lastNameEnglish"));
            person.setDegreeEnglish(rs.getString("degreeEnglish"));
            person.setPhone(rs.getString("phone"));
            person.setFax(rs.getString("fax"));
            person.setEmail(rs.getString("email"));
            person.setDepartment(rs.getString("department"));
            person.setFacultyId(rs.getInt("facultyId"));
            person.setAddress(rs.getString("address"));
            person.setHomePhone(rs.getString("homePhone"));
            person.setCellPhone(rs.getString("cellPhone"));
            person.setRoomNumber(rs.getString("roomNumber"));
            person.setResearchEnabled(rs.getBoolean("researchEnabled"));
            person.setPreferedLocaleId(rs.getString("preferedLocaleId"));
            person.setAcademicTitle(rs.getString("academicTitle"));
            person.setWebsiteUrl(rs.getString("websiteUrl"));
            person.setCampusId(rs.getInt("campusId"));

            person.setPostReceiveDays(BaseUtils.getIntegerList(rs.getString("postReceiveDays"),","));
            person.setPostReceiveHour(rs.getInt("postReceiveHour"));
            person.setPostReceiveImmediately(rs.getBoolean("postReceiveImmediately"));
            person.setReadsUTF8Mails(rs.getBoolean("readsUTF8Mails"));
            person.setReceivePosts(rs.getBoolean("receivePosts"));
            person.setPostNewDesign(rs.getBoolean("postNewDesign"));
            person.setImageUrl(rs.getString("imageUrl"));
            person.setCollectPublications(rs.getBoolean("collectPublications"));
            person.setLastSync(rs.getTimestamp("lastSync"));
            return person;
        }
}
