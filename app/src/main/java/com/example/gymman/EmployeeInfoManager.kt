import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

class EmployeeInfoManager() {

    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
//    val employeeInfoManager = EmployeeInfoManager(db, auth)
    val employeesRef = db.collection("employees")

    // 회원 정보 추가
    fun addEmployee(employee: Employee) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            currentUser.email?.let { employeesRef.document(it).set(employee) }
        }
    }


    // 회원 정보 업데이트
    fun updateEmployee(employeeId: String, employee: Employee) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            employeesRef.document(employeeId).set(employee)
        }
    }


    // 회원 정보 삭제
    fun deleteEmployee(employeeId: String) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            employeesRef.document(employeeId).delete()
        }
    }


}

data class Employee(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val hireDate: Date = Date(),
    val resignDate: Date = Date(),
    val position: String = "",
    val basicSalary: Int = 0,
    val memberCount: Int = 0,
)