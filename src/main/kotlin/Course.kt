class Course(
        val name: String,
        var studentsCount: Int,
        val maxStudentsCount: Int,
        val groups: List<String>,
        val students: MutableList<Student> = mutableListOf()
){
        fun addStudent(student: Student): Boolean{
                if (student.group in groups){
                        students.add(student)
                        studentsCount += 1
                        return true
                }
                return false
        }

        fun getOccupancy() = 100.0 * studentsCount / maxStudentsCount

        override fun toString() = name
}