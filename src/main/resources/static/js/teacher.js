// 加载学期列表
function loadSemesters() {
    $.get('/student-system/teacher/semesters', function(result) {
        if (result.code === 200) {
            var html = '<option value="">全部学期</option>';
            result.data.forEach(function(semester) {
                html += '<option value="' + semester + '">' + semester + '</option>';
            });
            $('#semesterSelect').html(html);
        }
    });
}

// 加载课程列表
function loadCourseList() {
    var semester = $('#semesterSelect').val();
    var url = '/student-system/teacher/course/list';
    if (semester) {
        url += '?semester=' + encodeURIComponent(semester);
    }
    
    $.get(url, function(result) {
        if (result.code === 200) {
            var html = '';
            var courseOptions = '<option value="">请选择课程</option>';
            var selectCourseOptions = '<option value="">请选择课程</option>';
            
            result.data.forEach(function(item) {
                html += '<tr>';
                html += '<td>' + item.id + '</td>';
                html += '<td>' + item.courseName + '</td>';
                html += '<td>' + item.courseCode + '</td>';
                html += '<td>' + (item.className || '') + '</td>';
                html += '<td>' + (item.semester || '') + '</td>';
                html += '<td>' + (item.description || '') + '</td>';
                html += '<td>';
                html += '<button class="btn btn-sm btn-info mr-1" onclick="selectCourse(' + item.id + ')">查看成绩</button>';
                html += '<button class="btn btn-sm btn-orange mr-1" onclick="manageSelectCourse(' + item.id + ')">选课管理</button>';
                html += '</td>';
                html += '</tr>';
                
                var courseText = item.courseName + ' (' + (item.semester || '') + ' - ' + (item.className || '') + ')';
                courseOptions += '<option value="' + item.id + '">' + courseText + '</option>';
                selectCourseOptions += '<option value="' + item.id + '">' + courseText + '</option>';
            });
            $('#courseTableBody').html(html);
            $('#courseSelect').html(courseOptions);
            $('#selectCourseSelect').html(selectCourseOptions);
            loadCourseOptions();
        }
    });
}

// 加载成绩列表
function loadScoreList() {
    var courseId = $('#courseSelect').val();
    if (!courseId) {
        $('#scoreTableBody').html('<tr><td colspan="7" class="text-center">请先选择课程</td></tr>');
        return;
    }
    
    $.get('/student-system/teacher/score/list', {courseId: courseId}, function(result) {
        if (result.code === 200) {
            var html = '';
            if (result.data.length === 0) {
                html = '<tr><td colspan="7" class="text-center">暂无成绩记录</td></tr>';
            } else {
                result.data.forEach(function(item) {
                    html += '<tr>';
                    html += '<td>' + item.id + '</td>';
                    html += '<td>' + (item.studentName || '') + '</td>';
                    html += '<td>' + (item.courseName || '') + '</td>';
                    html += '<td>' + (item.className || '') + '</td>';
                    html += '<td>' + item.score + '</td>';
                    html += '<td>' + (item.remark || '') + '</td>';
                    html += '<td>';
                    html += '<button class="btn btn-sm btn-orange mr-1" onclick="editScore(' + item.id + ')">编辑</button>';
                    html += '<button class="btn btn-sm btn-danger" onclick="deleteScore(' + item.id + ')">删除</button>';
                    html += '</td>';
                    html += '</tr>';
                });
            }
            $('#scoreTableBody').html(html);
        } else {
            alert(result.message || '加载失败');
        }
    });
}

function selectCourse(courseId) {
    $('#courseSelect').val(courseId);
    loadScoreList();
}

function manageSelectCourse(courseId) {
    $('#selectCourseSelect').val(courseId);
    loadStudentCourses();
}

// 加载选课列表
function loadStudentCourses() {
    var courseId = $('#selectCourseSelect').val();
    if (!courseId) {
        $('#studentCourseTableBody').html('<tr><td colspan="7" class="text-center">请先选择课程</td></tr>');
        return;
    }
    
    $.get('/student-system/teacher/course/student-courses', {courseId: courseId}, function(result) {
        if (result.code === 200) {
            var html = '';
            if (result.data.length === 0) {
                html = '<tr><td colspan="7" class="text-center">暂无选课记录</td></tr>';
            } else {
                result.data.forEach(function(item) {
                    var date = new Date(item.createdTime);
                    var dateStr = date.getFullYear() + '-' + 
                        String(date.getMonth() + 1).padStart(2, '0') + '-' + 
                        String(date.getDate()).padStart(2, '0') + ' ' +
                        String(date.getHours()).padStart(2, '0') + ':' + 
                        String(date.getMinutes()).padStart(2, '0');
                    
                    html += '<tr>';
                    html += '<td>' + item.id + '</td>';
                    html += '<td>' + (item.studentName || '') + '</td>';
                    html += '<td>' + (item.courseName || '') + '</td>';
                    html += '<td>' + (item.className || '') + '</td>';
                    html += '<td>' + (item.semester || '') + '</td>';
                    html += '<td>' + dateStr + '</td>';
                    html += '<td>';
                    html += '<button class="btn btn-sm btn-danger" onclick="deleteSelectCourse(' + item.studentId + ',' + item.courseId + ')">取消选课</button>';
                    html += '</td>';
                    html += '</tr>';
                });
            }
            $('#studentCourseTableBody').html(html);
        } else {
            alert(result.message || '加载失败');
        }
    });
}

// 加载课程下拉选项
function loadCourseOptions() {
    var semester = $('#semesterSelect').val();
    var url = '/student-system/teacher/course/options';
    if (semester) {
        url += '?semester=' + encodeURIComponent(semester);
    }
    
    $.get(url, function(result) {
        if (result.code === 200) {
            var html = '<option value="">请选择</option>';
            result.data.forEach(function(item) {
                var courseText = item.courseName + ' (' + (item.semester || '') + ' - ' + (item.className || '') + ')';
                html += '<option value="' + item.id + '">' + courseText + '</option>';
            });
            $('#scoreCourseId').html(html);
        }
    });
}

// 加载学生选项（根据课程，只显示已选课的学生）
function loadStudentOptions(courseId) {
    if (!courseId) {
        $('#scoreStudentId').html('<option value="">请先选择课程</option>');
        return;
    }
    
    $.get('/student-system/teacher/course/students', {courseId: courseId}, function(result) {
        if (result.code === 200) {
            var html = '<option value="">请选择</option>';
            if (result.data.length === 0) {
                html = '<option value="">该课程暂无选课学生</option>';
            } else {
                result.data.forEach(function(item) {
                    html += '<option value="' + item.id + '">' + item.name + '</option>';
                });
            }
            $('#scoreStudentId').html(html);
        } else {
            alert(result.message || '加载学生列表失败');
        }
    });
}

// 加载班级学生（用于选课）
function loadClassStudents() {
    var courseId = $('#selectCourseId').val();
    if (!courseId) {
        $('#selectStudentId').html('<option value="">请先选择课程</option>');
        return;
    }
    
    $.get('/student-system/teacher/course/class-students', {courseId: courseId}, function(result) {
        if (result.code === 200) {
            var html = '<option value="">请选择</option>';
            result.data.forEach(function(item) {
                html += '<option value="' + item.id + '">' + item.name + '</option>';
            });
            $('#selectStudentId').html(html);
        } else {
            alert(result.message || '加载学生列表失败');
        }
    });
}

function showSelectCourseModal() {
    $('#selectCourseForm')[0].reset();
    loadCourseOptions();
    $('#selectStudentId').html('<option value="">请先选择课程</option>');
    $('#selectCourseModal').modal('show');
}

function saveSelectCourse() {
    var courseId = $('#selectCourseId').val();
    var studentId = $('#selectStudentId').val();
    
    if (!courseId || !studentId) {
        alert('请选择课程和学生');
        return;
    }
    
    $.post('/student-system/teacher/course/select', {
        courseId: courseId,
        studentId: studentId
    }, function(result) {
        if (result.code === 200) {
            alert('选课成功');
            $('#selectCourseModal').modal('hide');
            loadStudentCourses();
        } else {
            alert(result.message || '选课失败');
        }
    });
}

function deleteSelectCourse(studentId, courseId) {
    if (!confirm('确定要取消该学生的选课吗？')) return;
    
    $.post('/student-system/teacher/course/unselect', {
        courseId: courseId,
        studentId: studentId
    }, function(result) {
        if (result.code === 200) {
            alert('取消选课成功');
            loadStudentCourses();
        } else {
            alert(result.message || '取消选课失败');
        }
    });
}

function showScoreModal() {
    $('#scoreForm')[0].reset();
    $('#scoreId').val('');
    loadCourseOptions();
    $('#scoreStudentId').html('<option value="">请先选择课程</option>');
    $('#scoreModal').modal('show');
}

$('#scoreCourseId').on('change', function() {
    loadStudentOptions($(this).val());
});

function editScore(id) {
    var courseId = $('#courseSelect').val();
    if (!courseId) {
        alert('请先选择课程');
        return;
    }
    
    $.get('/student-system/teacher/score/list', {courseId: courseId}, function(result) {
        if (result.code === 200) {
            var item = result.data.find(function(s) { return s.id === id; });
            if (item) {
                $('#scoreId').val(item.id);
                $('#scoreCourseId').val(item.courseId);
                $('#scoreValue').val(item.score);
                $('#scoreRemark').val(item.remark || '');
                loadCourseOptions();
                loadStudentOptions(item.courseId);
                setTimeout(function() {
                    $('#scoreCourseId').val(item.courseId);
                    $('#scoreStudentId').val(item.studentId);
                }, 200);
                $('#scoreModal').modal('show');
            }
        }
    });
}

function saveScore() {
    var scoreValue = parseFloat($('#scoreValue').val());
    if (isNaN(scoreValue) || scoreValue < 0 || scoreValue > 100) {
        alert('分数必须在0-100之间');
        return;
    }
    
    var data = {
        id: $('#scoreId').val() || null,
        studentId: $('#scoreStudentId').val(),
        courseId: $('#scoreCourseId').val(),
        score: scoreValue,
        remark: $('#scoreRemark').val()
    };
    
    var url = data.id ? '/student-system/teacher/score/update' : '/student-system/teacher/score/save';
    
    $.post(url, data, function(result) {
        if (result.code === 200) {
            alert('保存成功');
            $('#scoreModal').modal('hide');
            loadScoreList();
        } else {
            alert(result.message || '保存失败');
        }
    });
}

function deleteScore(id) {
    if (!confirm('确定要删除吗？')) return;
    $.post('/student-system/teacher/score/delete', {id: id}, function(result) {
        if (result.code === 200) {
            alert('删除成功');
            loadScoreList();
        } else {
            alert(result.message || '删除失败');
        }
    });
}

// 初始化
$(function() {
    loadSemesters();
    loadCourseList();
});
