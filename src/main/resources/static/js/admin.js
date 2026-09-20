// 页面切换
$('.sidebar .nav-link').on('click', function(e) {
    e.preventDefault();
    $('.sidebar .nav-link').removeClass('active');
    $(this).addClass('active');
    $('.page-content').hide();
    var page = $(this).data('page');
    $('#' + page + '-page').show();
    loadPageData(page);
});

// 加载页面数据
function loadPageData(page) {
    if (page === 'class') {
        loadClassList();
    } else if (page === 'course') {
        loadCourseList();
        loadTeacherOptions();
        loadClassOptions();
    } else if (page === 'student') {
        loadStudentList();
        loadClassOptions();
    } else if (page === 'score') {
        loadScoreList();
        loadStudentOptions();
        loadCourseOptions();
    }
}

// ========== 班级管理 ==========
function loadClassList() {
    $.get('/student-system/admin/class/list', function(result) {
        if (result.code === 200) {
            var html = '';
            result.data.forEach(function(item) {
                html += '<tr>';
                html += '<td>' + item.id + '</td>';
                html += '<td>' + item.className + '</td>';
                html += '<td>' + item.classCode + '</td>';
                html += '<td>' + (item.description || '') + '</td>';
                html += '<td>';
                html += '<button class="btn btn-sm btn-orange mr-1" onclick="editClass(' + item.id + ')">编辑</button>';
                html += '<button class="btn btn-sm btn-danger" onclick="deleteClass(' + item.id + ')">删除</button>';
                html += '</td>';
                html += '</tr>';
            });
            $('#classTableBody').html(html);
        }
    });
}

function showClassModal() {
    $('#classForm')[0].reset();
    $('#classId').val('');
    $('#classModal').modal('show');
}

function editClass(id) {
    $.get('/student-system/admin/class/list', function(result) {
        if (result.code === 200) {
            var item = result.data.find(function(c) { return c.id === id; });
            if (item) {
                $('#classId').val(item.id);
                $('#className').val(item.className);
                $('#classCode').val(item.classCode);
                $('#classDescription').val(item.description || '');
                $('#classModal').modal('show');
            }
        }
    });
}

function saveClass() {
    var data = {
        id: $('#classId').val() || null,
        className: $('#className').val(),
        classCode: $('#classCode').val(),
        description: $('#classDescription').val()
    };
    
    var url = data.id ? '/student-system/admin/class/update' : '/student-system/admin/class/save';
    
    $.post(url, data, function(result) {
        if (result.code === 200) {
            alert('保存成功');
            $('#classModal').modal('hide');
            loadClassList();
        } else {
            alert(result.message || '保存失败');
        }
    });
}

function deleteClass(id) {
    if (!confirm('确定要删除吗？')) return;
    $.post('/student-system/admin/class/delete', {id: id}, function(result) {
        if (result.code === 200) {
            alert('删除成功');
            loadClassList();
        } else {
            alert(result.message || '删除失败');
        }
    });
}

// ========== 课程管理 ==========
function loadCourseList() {
    $.get('/student-system/admin/course/list', function(result) {
        if (result.code === 200) {
            var html = '';
            result.data.forEach(function(item) {
                html += '<tr>';
                html += '<td>' + item.id + '</td>';
                html += '<td>' + item.courseName + '</td>';
                html += '<td>' + item.courseCode + '</td>';
                html += '<td>' + (item.teacherName || '') + '</td>';
                html += '<td>' + (item.className || '') + '</td>';
                html += '<td>' + (item.semester || '') + '</td>';
                html += '<td>' + (item.description || '') + '</td>';
                html += '<td>';
                html += '<button class="btn btn-sm btn-orange mr-1" onclick="editCourse(' + item.id + ')">编辑</button>';
                html += '<button class="btn btn-sm btn-danger" onclick="deleteCourse(' + item.id + ')">删除</button>';
                html += '</td>';
                html += '</tr>';
            });
            $('#courseTableBody').html(html);
        }
    });
}

function loadTeacherOptions() {
    $.get('/student-system/admin/teacher/options', function(result) {
        if (result.code === 200) {
            var html = '<option value="">请选择</option>';
            result.data.forEach(function(item) {
                html += '<option value="' + item.id + '">' + item.name + '</option>';
            });
            $('#courseTeacherId').html(html);
        }
    });
}

function loadClassOptions() {
    $.get('/student-system/admin/class/options', function(result) {
        if (result.code === 200) {
            var html = '<option value="">请选择</option>';
            result.data.forEach(function(item) {
                html += '<option value="' + item.id + '">' + item.className + '</option>';
            });
            $('#courseClassId').html(html);
            $('#studentClassId').html(html);
        }
    });
}

function showCourseModal() {
    $('#courseForm')[0].reset();
    $('#courseId').val('');
    loadTeacherOptions();
    loadClassOptions();
    $('#courseModal').modal('show');
}

function editCourse(id) {
    $.get('/student-system/admin/course/list', function(result) {
        if (result.code === 200) {
            var item = result.data.find(function(c) { return c.id === id; });
            if (item) {
                $('#courseId').val(item.id);
                $('#courseName').val(item.courseName);
                $('#courseCode').val(item.courseCode);
                $('#courseTeacherId').val(item.teacherId);
                $('#courseClassId').val(item.classId);
                $('#courseSemester').val(item.semester || '');
                $('#courseDescription').val(item.description || '');
                loadTeacherOptions();
                loadClassOptions();
                setTimeout(function() {
                    $('#courseTeacherId').val(item.teacherId);
                    $('#courseClassId').val(item.classId);
                }, 100);
                $('#courseModal').modal('show');
            }
        }
    });
}

function saveCourse() {
    var data = {
        id: $('#courseId').val() || null,
        courseName: $('#courseName').val(),
        courseCode: $('#courseCode').val(),
        teacherId: $('#courseTeacherId').val(),
        classId: $('#courseClassId').val(),
        semester: $('#courseSemester').val(),
        description: $('#courseDescription').val()
    };
    
    var url = data.id ? '/student-system/admin/course/update' : '/student-system/admin/course/save';
    
    $.post(url, data, function(result) {
        if (result.code === 200) {
            alert('保存成功');
            $('#courseModal').modal('hide');
            loadCourseList();
        } else {
            alert(result.message || '保存失败');
        }
    });
}

function deleteCourse(id) {
    if (!confirm('确定要删除吗？')) return;
    $.post('/student-system/admin/course/delete', {id: id}, function(result) {
        if (result.code === 200) {
            alert('删除成功');
            loadCourseList();
        } else {
            alert(result.message || '删除失败');
        }
    });
}

// ========== 学生管理 ==========
function loadStudentList() {
    $.get('/student-system/admin/student/list', function(result) {
        if (result.code === 200) {
            var html = '';
            result.data.forEach(function(item) {
                html += '<tr>';
                html += '<td>' + item.id + '</td>';
                html += '<td>' + item.username + '</td>';
                html += '<td>' + item.name + '</td>';
                html += '<td>' + (item.className || '') + '</td>';
                html += '<td>';
                html += '<button class="btn btn-sm btn-orange mr-1" onclick="editStudent(' + item.id + ')">编辑</button>';
                html += '<button class="btn btn-sm btn-danger" onclick="deleteStudent(' + item.id + ')">删除</button>';
                html += '</td>';
                html += '</tr>';
            });
            $('#studentTableBody').html(html);
        }
    });
}

function showStudentModal() {
    $('#studentForm')[0].reset();
    $('#studentId').val('');
    loadClassOptions();
    $('#studentModal').modal('show');
}

function editStudent(id) {
    $.get('/student-system/admin/student/list', function(result) {
        if (result.code === 200) {
            var item = result.data.find(function(s) { return s.id === id; });
            if (item) {
                $('#studentId').val(item.id);
                $('#studentUsername').val(item.username);
                $('#studentPassword').val('');
                $('#studentName').val(item.name);
                loadClassOptions();
                setTimeout(function() {
                    $('#studentClassId').val(item.classId);
                }, 100);
                $('#studentModal').modal('show');
            }
        }
    });
}

function saveStudent() {
    var data = {
        id: $('#studentId').val() || null,
        username: $('#studentUsername').val(),
        password: $('#studentPassword').val(),
        name: $('#studentName').val(),
        classId: $('#studentClassId').val()
    };
    
    if (!data.password && !data.id) {
        alert('请输入密码');
        return;
    }
    
    var url = data.id ? '/student-system/admin/student/update' : '/student-system/admin/student/save';
    
    $.post(url, data, function(result) {
        if (result.code === 200) {
            alert('保存成功');
            $('#studentModal').modal('hide');
            loadStudentList();
        } else {
            alert(result.message || '保存失败');
        }
    });
}

function deleteStudent(id) {
    if (!confirm('确定要删除吗？')) return;
    $.post('/student-system/admin/student/delete', {id: id}, function(result) {
        if (result.code === 200) {
            alert('删除成功');
            loadStudentList();
        } else {
            alert(result.message || '删除失败');
        }
    });
}

// ========== 成绩管理 ==========
function loadScoreList() {
    $.get('/student-system/admin/score/list', function(result) {
        if (result.code === 200) {
            var html = '';
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
            $('#scoreTableBody').html(html);
        }
    });
}

function loadStudentOptions() {
    $.get('/student-system/admin/student/options', function(result) {
        if (result.code === 200) {
            var html = '<option value="">请选择</option>';
            result.data.forEach(function(item) {
                html += '<option value="' + item.id + '">' + item.name + '</option>';
            });
            $('#scoreStudentId').html(html);
        }
    });
}

function loadCourseOptions() {
    $.get('/student-system/admin/course/options', function(result) {
        if (result.code === 200) {
            var html = '<option value="">请选择</option>';
            result.data.forEach(function(item) {
                html += '<option value="' + item.id + '">' + item.courseName + '</option>';
            });
            $('#scoreCourseId').html(html);
        }
    });
}

function showScoreModal() {
    $('#scoreForm')[0].reset();
    $('#scoreId').val('');
    loadStudentOptions();
    loadCourseOptions();
    $('#scoreModal').modal('show');
}

function editScore(id) {
    $.get('/student-system/admin/score/list', function(result) {
        if (result.code === 200) {
            var item = result.data.find(function(s) { return s.id === id; });
            if (item) {
                $('#scoreId').val(item.id);
                $('#scoreStudentId').val(item.studentId);
                $('#scoreCourseId').val(item.courseId);
                $('#scoreValue').val(item.score);
                $('#scoreRemark').val(item.remark || '');
                loadStudentOptions();
                loadCourseOptions();
                setTimeout(function() {
                    $('#scoreStudentId').val(item.studentId);
                    $('#scoreCourseId').val(item.courseId);
                }, 100);
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
    
    var url = data.id ? '/student-system/admin/score/update' : '/student-system/admin/score/save';
    
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
    $.post('/student-system/admin/score/delete', {id: id}, function(result) {
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
    loadPageData('class');
});


