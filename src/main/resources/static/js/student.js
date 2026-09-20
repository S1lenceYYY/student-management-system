// 加载课程列表
function loadCourseList() {
    $.get('/student-system/student/course/list', function(result) {
        if (result.code === 200) {
            var html = '';
            if (result.data.length === 0) {
                html = '<tr><td colspan="6" class="text-center">暂无课程</td></tr>';
            } else {
                result.data.forEach(function(item) {
                    html += '<tr>';
                    html += '<td>' + item.id + '</td>';
                    html += '<td>' + item.courseName + '</td>';
                    html += '<td>' + item.courseCode + '</td>';
                    html += '<td>' + (item.teacherName || '') + '</td>';
                    html += '<td>' + (item.className || '') + '</td>';
                    html += '<td>' + (item.description || '') + '</td>';
                    html += '</tr>';
                });
            }
            $('#courseTableBody').html(html);
        } else {
            alert(result.message || '加载失败');
        }
    });
}

// 加载成绩列表
function loadScoreList() {
    $.get('/student-system/student/score/list', function(result) {
        if (result.code === 200) {
            var html = '';
            if (result.data.length === 0) {
                html = '<tr><td colspan="5" class="text-center">暂无成绩记录</td></tr>';
            } else {
                result.data.forEach(function(item) {
                    html += '<tr>';
                    html += '<td>' + item.id + '</td>';
                    html += '<td>' + (item.courseName || '') + '</td>';
                    html += '<td>' + (item.className || '') + '</td>';
                    html += '<td>' + item.score + '</td>';
                    html += '<td>' + (item.remark || '') + '</td>';
                    html += '</tr>';
                });
            }
            $('#scoreTableBody').html(html);
        } else {
            alert(result.message || '加载失败');
        }
    });
}

// 初始化
$(function() {
    loadCourseList();
    loadScoreList();
});







