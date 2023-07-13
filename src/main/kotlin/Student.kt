import kotlin.math.min

class Student(
        val name: String,
        val group: String,
        val priorities: List<String>,
        val averageScores: List<Double>,
): Comparable<Student>{
        override fun compareTo(other: Student): Int {
                return compareScores(this.averageScores, other.averageScores)
        }

        override fun toString() = name

        private fun compareScores(arr1: List<Double>, arr2: List<Double>, index: Int = 0): Int{
                if (index >= min(arr1.size, arr2.size)) return 0
                if (arr1[index] > arr2[index]) return 1
                if (arr1[index] < arr2[index]) return -1
                return compareScores(arr1, arr2, index + 1)
        }
}
