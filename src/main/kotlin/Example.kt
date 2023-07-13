import java.io.File

class Example {
    private var courses: List<Course>
    private var students: List<Student>

    init {
        val allGroups = listOf("0", "1", "2", "3", "4")
        val firstCourseName = "first course"
        val firstCourse = Course(firstCourseName, 0, 3, allGroups.slice(1..4))
        val secondCourseName = "second course"
        val secondCourse = Course(secondCourseName, 0, 2, allGroups)
        courses = listOf(firstCourse, secondCourse)
        val scores0 = listOf(5.0, 5.0, 5.0)
        val scores1 = listOf(5.0, 4.0, 5.0)
        val scores2 = listOf(4.0, 5.0, 5.0)
        val scores3 = listOf(4.0, 5.0, 5.0)
        val scores4 = listOf(4.0, 4.0, 4.0)
        students = listOf(
            Student("student0", "0", listOf(firstCourseName, secondCourseName), scores0),
            Student("student1", "1", listOf(firstCourseName, secondCourseName), scores1),
            Student("student2", "2", listOf(secondCourseName, firstCourseName), scores2),
            Student("student3", "3", listOf(secondCourseName, firstCourseName), scores3),
            Student("student4", "4", listOf(secondCourseName, firstCourseName), scores4),
        ).shuffled()
    }

    fun writeExampleData(){
        val file1 = File("Выбор ДВС 3 курс 6 семестр.txt")
        file1.writeText("Отметка времени\tФамилия Имя\tНомер группы\tДисциплина (приоритет 1)\tДисциплина (приоритет 2)\tДисциплина (приоритет 3)\n")
        var text1 = ""
        val time = "2022-11-24 10:07:08.114"
        for (student in students) {
            if (text1 != "")
                text1 += "\n"
            var string = time + "\t" + student.name + "\t" + student.group
            for (course in student.priorities)
                string += "\t" + course
            text1 += string
        }
        file1.writeText(text1)
        val file2 = File("Средние баллы.txt")
        var text2 = ""
        for (student in students) {
            if (text2 != "")
                text2 += "\n"
            var string = student.name
            for (score in student.averageScores)
                string += "\t" + score
            text2 += string
        }
        file2.writeText(text2)
        val file3 = File("Список вариантов.txt")
        var text3 = ""
        for (course in courses){
            if (text3 != "")
                text3 += "\n"
            var string = course.name + "\t" + course.maxStudentsCount + "\t" + course.groups[0]
            for (i in 1 until course.groups.size)
                string += "," + course.groups[i]
            text3 += string
        }
        file3.writeText(text3)
    }
}