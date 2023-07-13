
import exceptions.NoSuchCourseException
import exceptions.NoSuchStudentException
import java.io.File

class Solution(
    listVarientsFileName: String,
    averageScoresFileName: String,
    studentChoicesFileName: String
) {
    private val courses = mutableListOf<Course>()
    private val scores = mutableMapOf<String, List<Double>>()
    private val uniqueStudentNames = mutableListOf<String>()
    private val students = mutableListOf<Student>()
    private val conflicts = mutableListOf<ConflictInfo>()
    private var firstConflictedStudent: Student? = null

    init {
        File(listVarientsFileName).forEachLine { addStringAsCourseInfo(it) }
        File(averageScoresFileName).forEachLine { addStringAsScoreInfo(it) }
        File(studentChoicesFileName).forEachLine { addStringAsStudentInfo(it) }
    }

    private fun addStringAsCourseInfo(string: String){
        val temp = string.split("\t")
        val name = temp[0]
        val maxStudentsCount = temp[1].toInt()
        val groups = temp[2].split(",")
        val students = mutableListOf<Student>()
        val course = Course(name,0,  maxStudentsCount, groups, students)
        courses.add(course)
    }

    private fun addStringAsScoreInfo(string: String){
        val temp = string.split("\t")
        val name = temp[0]
        val averageScores = mutableListOf<Double>()
        temp.drop(1).forEach { averageScores.add(it.toDouble()) }
        scores[name] = averageScores
    }

    private fun addStringAsStudentInfo(string: String){
        val temp = string.split("\t").drop(1)
        val name = temp[0]
        val groupNumber = temp[1]
        val priorities = temp.drop(2)
        if (name in scores.keys){
            if (name in uniqueStudentNames){
                val oldStudent = findStudentByName(name)
                students.remove(oldStudent)
            }
            else
                uniqueStudentNames.add(name)
            val averageScores = scores[name]
            val newStudent = Student(name, groupNumber, priorities, averageScores!!)
            students.add(newStudent)
        }
    }

    private fun findStudentByName(name: String): Student{
        for (student in students)
            if (student.name == name)
                return student
        throw NoSuchStudentException()
    }

    private fun findCourseByName(name: String): Course{
        for (course in courses)
            if (course.name == name)
                return course
        throw NoSuchCourseException()
    }

    private fun compareWithLastOnCourse(student: Student, course: Course): Boolean{
        return student.compareTo(course.students[course.maxStudentsCount - 1]) == 0
    }

    private fun distributeStudents(){
        students.sortDescending()
        for (i in 0 until students.size){
            val student = students[i]
            for (k in 0 until student.priorities.size){
                val course = findCourseByName(student.priorities[k])
                if (course.studentsCount < course.maxStudentsCount || compareWithLastOnCourse(student, course)){
                    val res = course.addStudent(student)
                    if (res) {
                        if (course.studentsCount == course.maxStudentsCount + 1 && firstConflictedStudent == null)
                            firstConflictedStudent = student
                        break
                    }
                }
            }
        }
    }

    private fun collectConflictInfo(){
        for (course in courses){
            if (course.getOccupancy() > 100){
                val conflict = ConflictInfo(course.name)
                for (student in course.students)
                    if (compareWithLastOnCourse(student, course))
                        conflict.students.add(student)
                conflicts.add(conflict)
            }
        }
    }

    private fun getDistributionInfo(): String{
        var str = ""
        for (course in courses) {
            str += "Курс $course заполнен на ${course.getOccupancy()}%\n"
            str += "На курсе будут учиться:\n"
            for (student in course.students)
                str += "\t" + student
            str += "\n\n"
        }
        return str
    }

    private fun getConflictInfo(): String{
        var str = ""
        for (conflict in conflicts){
            str += "Направлении ${conflict.courseName} переполнено, потому что есть ${conflict.students.size} студентов с одинаковыми баллами\n"
            for (student in conflict.students)
                str += "\t" + student
            str += "\n"
        }
        return str
    }

    private fun getFirstConflictedStudentInfo(): String {
        return if (firstConflictedStudent == null)
            "Проблем нет"
        else
            "Первый студент, расширивший направление - $firstConflictedStudent"
    }

    private fun printDistribution(){
        println(getDistributionInfo())
    }

    private fun printConflictInfo(){
        println(getConflictInfo())
    }

    private fun printFirstConflictedStudentInfo(){
        println(getFirstConflictedStudentInfo())
    }

    private fun saveDistribution(){
        val file = File("distribution.txt")
        file.writeText(getDistributionInfo(), charset = Charsets.UTF_8)
    }

    private fun saveConflictInfo(){
        val file = File("conflict_info.txt")
        file.writeText(getConflictInfo(), charset = Charsets.UTF_8)
    }

    private fun saveFirstConflictedStudentInfo(){
        val file = File("first_conflicted_student.txt")
        file.writeText(getFirstConflictedStudentInfo(), charset = Charsets.UTF_8)
    }

    private fun printResult(){
        printDistribution()
        println()
        printConflictInfo()
        println()
        printFirstConflictedStudentInfo()
    }

    private fun saveResult(){
        saveDistribution()
        saveConflictInfo()
        saveFirstConflictedStudentInfo()
    }

    fun solve(){
        distributeStudents()
        collectConflictInfo()
        printResult()
        saveResult()
    }
}