import axios from 'axios'

const state = {
    user: null,
    isAdmin: null,
    activeUsers: null,
    deactivatedUsers: null,
    activeTeachers: null,
    deactivatedTeachers: null,
    teacherAvailabilities: null,
    activeCourses: null,
    deactivatedCourses: null,
    teacherCourses: null,
    allBookings: null,
    allActiveBookings: null,
    allCompletedBookings: null,
    allDeletedBookings: null,
    personalBookings: null,
    personalActiveBookings: null,
    personalCompletedBookings: null,
    personalDeletedBookings: null,
    teacherByCourse: null,
    activeTimeSlots: null,
    deactivatedTimeSlots: null
}

const getters = {
    StateUser: state => state.user,
    isAuthenticated: state => !!state.user,
    isAdmin: state => state.isAdmin,
    StateActiveUsers: state => state.activeUsers,
    StateDeactivatedUsers: state => state.deactivatedUsers,
    StateActiveTeachers: state => state.activeTeachers,
    StateDeactivatedTeachers: state => state.deactivatedTeachers,
    StateTeacherAvailabilities: state => state.teacherAvailabilities,
    StateActiveCourses: state => state.activeCourses,
    StateDeactivatedCourses: state => state.deactivatedCourses,
    StateTeacherCourses: state => state.teacherCourses,
    StateAllBookings: state => state.allBookings,
    StateAllActiveBookings: state => state.allActiveBookings,
    StateAllCompletedBookings: state => state.allCompletedBookings,
    StateAllDeletedBookings: state => state.allDeletedBookings,
    StatePersonalBookings: state => state.personalBookings,
    StateActiveBookings: state => state.personalActiveBookings,
    StateCompletedBookings: state => state.personalCompletedBookings,
    StateDeletedBookings: state => state.personalDeletedBookings,
    StateTeacherByCourse: state => state.teacherByCourse,
    StateActiveTimeSlots: state => state.activeTimeSlots,
    StateDeactivatedTimeSlots: state => state.deactivatedTimeSlots
}

const actions = {

    // POST METHODS
    async LogIn({
        commit
    }, User) {
        let isAdmin = await axios.post('http://localhost:8080/ServletController?action=login', User)
        await commit('loginUser', User.email, isAdmin)
    },

    async LogOut({
        commit
    }) {
        await axios.post('http://localhost:8080/ServletController?action=logout')
        let user = null
        await commit('logoutUser', user)
    },

    async InsertUser(newUser) {
        await axios.post('http://localhost:8080/ServletController?action=insert-user', newUser)
    },

    async ActivateUser(User) {
        await axios.post('http://localhost:8080/ServletController?action=activate-user', User)
    },

    async DectivateUser(User) {
        await axios.post('http://localhost:8080/ServletController?action=deactivate-user', User)
    },

    async InsertTeacher(newTeacher) {
        await axios.post('http://localhost:8080/ServletController?action=insert-teacher', newTeacher)
    },

    async ActivateTeacher(Teacher) {
        await axios.post('http://localhost:8080/ServletController?action=activate-teacher', Teacher)
    },

    async DectivateTeacher(Teacher) {
        await axios.post('http://localhost:8080/ServletController?action=deactivate-teacher', Teacher)
    },

    async InsertCourse(newCourse) {
        await axios.post('http://localhost:8080/ServletController?action=insert-course', newCourse)
    },

    async ActivateCourse(Course) {
        await axios.post('http://localhost:8080/ServletController?action=activate-course', Course)
    },

    async DectivateCourse(Course) {
        await axios.post('http://localhost:8080/ServletController?action=deactivate-course', Course)
    },

    async InsertTimeSlot(newTimeSlot) {
        await axios.post('http://localhost:8080/ServletController?action=insert-time-slot', newTimeSlot)
    },

    async ActivateTimeSlot(TimeSlot) {
        await axios.post('http://localhost:8080/ServletController?action=activate-time-slot', TimeSlot)
    },

    async DectivateTimeSlot(TimeSlot) {
        await axios.post('http://localhost:8080/ServletController?action=deactivate-time-slot', TimeSlot)
    },

    async AssignTeaching(Teacher, Course) {
        await axios.post('http://localhost:8080/ServletController?action=assign-teaching', Teacher, Course)
    },

    async ActivateTeaching(Teacher, Course) {
        await axios.post('http://localhost:8080/ServletController?action=activate-teaching', Teacher, Course)
    },

    async DeactivateTeaching(Teacher, Course) {
        await axios.post('http://localhost:8080/ServletController?action=deactivate-teaching', Teacher, Course)
    },

    async InsertBooking(User, Teacher, Course, TimeSlot) {
        await axios.post('http://localhost:8080/ServletController?action=insert-booking', User, Teacher, Course, TimeSlot)
    },

    async DeleteBooking(User, Teacher, Course, TimeSlot) {
        await axios.post('http://localhost:8080/ServletController?action=delete-booking', User, Teacher, Course, TimeSlot)
    },

    async CompleteBooking(User, Teacher, Course, TimeSlot) {
        await axios.post('http://localhost:8080/ServletController?action=complete-booking', User, Teacher, Course, TimeSlot)
    },

    // GET METHODS
    async GetActiveUsers({
        commit
    }) {
        let activeUsers = await axios.get('http://localhost:8080/ServletController?action=list-active-users')
        await commit('setActiveUsers', activeUsers)
    },

    async GetDeactivatedUsers({
        commit
    }) {
        let deactivatedUsers = await axios.get('http://localhost:8080/ServletController?action=list-deactivated-users')
        await commit('setDeactivatedUsers', deactivatedUsers)
    },

    async GetActiveTeachers({
        commit
    }) {
        let activeTeachers = await axios.get('http://localhost:8080/ServletController?action=list-active-teachers')
        await commit('setActiveTeachers', activeTeachers)
    },

    async GetDeactivatedTeachers({
        commit
    }) {
        let deactivatedTeachers = await axios.get('http://localhost:8080/ServletController?action=list-deactivated-teachers')
        await commit('setDeactivatedTeachers', deactivatedTeachers)
    },

    async GetTeacherAvailability({
        commit
    }, Teacher) {
        let teacherAvailabilities = await axios.get('http://localhost:8080/ServletController?action=list-teachers-availability', Teacher)
        await commit('setTeacherAvailabilities', teacherAvailabilities)
    },

    async GetActiveCourses({
        commit
    }) {
        let activeCourses = await axios.get('http://localhost:8080/ServletController?action=list-active-courses')
        await commit('setActiveCourses', activeCourses)
    },

    async GetDeactivatedCourses({
        commit
    }) {
        let deactivatedCourses = await axios.get('http://localhost:8080/ServletController?action=list-deactivated-courses')
        await commit('setDeactivatedCourses', deactivatedCourses)
    },

    async GetTeacherCourses({
        commit
    }) {
        let teacherCourses = await axios.get('http://localhost:8080/ServletController?action=list-teacher-courses')
        await commit('setTeacherCourses', teacherCourses)
    },

    async GetAllBookings({
        commit
    }) {
        let allBookings = await axios.get('http://localhost:8080/ServletController?action=list-all-bookings')
        await commit('setAllBookings', allBookings)
    },

    async GetAllActiveBookings({
        commit
    }) {
        let allActiveBookings = await axios.get('http://localhost:8080/ServletController?action=list-all-active-bookings')
        await commit('setAllActiveBookings', allActiveBookings)
    },

    async GetAllCompletedBookings({
        commit
    }) {
        let allCompletedBookings = await axios.get('http://localhost:8080/ServletController?action=list-all-completed-bookings')
        await commit('setAllCompletedBookings', allCompletedBookings)
    },

    async GetAllDeletedBookings({
        commit
    }) {
        let allDeletedBookings = await axios.get('http://localhost:8080/ServletController?action=list-all-deleted-bookings')
        await commit('setAllDeletedBookings', allDeletedBookings)
    },

    async GetPersonalBooking({
        commit
    }, User) {
        let personalBookings = await axios.get('http://localhost:8080/ServletController?action=list-personal-bookings', User)
        await commit('setPersonalBookings', personalBookings)
    },

    async GetPersonalActiveBooking({
        commit
    }, User) {
        let personalActiveBookings = await axios.get('http://localhost:8080/ServletController?action=list-personal-active-bookings', User)
        await commit('setPersonalActiveBookings', personalActiveBookings)
    },

    async GetPersonalCompletedBooking({
        commit
    }, User) {
        let personalCompletedBookings = await axios.get('http://localhost:8080/ServletController?action=list-personal-completed-bookings', User)
        await commit('setPersonalCompletedBookings', personalCompletedBookings)
    },

    async GetPersonalDeletedBooking({
        commit
    }, User) {
        let personalDeletedBookings = await axios.get('http://localhost:8080/ServletController?action=list-personal-deleted-bookings', User)
        await commit('setPersonalDeletedBookings', personalDeletedBookings)
    },

    async GetTeacherByCourse({
        commit
    }, Course) {
        let teacherByCourse = await axios.get('http://localhost:8080/ServletController?action=list-teacher-by-course', Course)
        await commit('setTeacherByCourse', teacherByCourse)
    },

    async GetActiveTimeSlots({
        commit
    }) {
        let activeTimeSlots = await axios.get('http://localhost:8080/ServletController?action=list-active-time-slots')
        await commit('setActiveTimeSlots', activeTimeSlots)
    },

    async GetDeactivatedTimeSlots({
        commit
    }) {
        let deactivatedTimeSlots = await axios.get('http://localhost:8080/ServletController?action=list-deactivated-time-slots')
        await commit('setDeactivatedTimeSlots', deactivatedTimeSlots)
    }
}

const mutations = {
    loginUser(state, email, isAdmin) {
        state.user = email
        state.isAdmin = isAdmin
    },

    logoutUser(state) {
        state.user = null
        state.posts = null
    },

    setActiveUsers(activeUsers) {
        state.activeUsers = activeUsers
    },

    setDeactivatedUsers(deactivatedUsers) {
        state.deactivatedUsers = deactivatedUsers
    },

    setActiveTeachers(activeTeachers) {
        state.activeTeachers = activeTeachers
    },

    setDeactivatedTeachers(deactivatedTeachers) {
        state.deactivatedTeachers = deactivatedTeachers
    },

    setTeacherAvailabilities(teacherAvailabilities) {
        state.teacherAvailabilities = teacherAvailabilities
    },

    setActiveCourses(activeCourses) {
        state.activeCourses = activeCourses
    },

    setDeactivatedCourses(deactivatedCourses) {
        state.deactivatedCourses = deactivatedCourses
    },

    setTeacherCourses(teacherCourses) {
        state.teacherCourses = teacherCourses
    },

    setAllBookings(allBookings) {
        state.allBookings = allBookings
    },

    setAllActiveBookings(allActiveBookings) {
        state.allActiveBookings = allActiveBookings
    },

    setAllCompletedBookings(allCompletedBookings) {
        state.allCompletedBookings = allCompletedBookings
    },

    setAllDeletedBookings(allDeletedBookings) {
        state.allDeletedBookings = allDeletedBookings
    },

    setPersonalBookings(personalBookings) {
        state.personalBookings = personalBookings
    },

    setPersonalActiveBookings(personalActiveBookings) {
        state.personalActiveBookings = personalActiveBookings
    },

    setPersonalCompletedBookings(personalCompletedBookings) {
        state.personalCompletedBookings = personalCompletedBookings
    },

    setPersonalDeletedBookings(personalDeletedBookings) {
        state.personalDeletedBookings = personalDeletedBookings
    },

    setTeacherByCourse(teacherByCourse) {
        state.teacherByCourse = teacherByCourse
    },

    setActiveTimeSlots(activeTimeSlots) {
        state.activeTimeSlots = activeTimeSlots
    },

    setDeactivatedTimeSlots(deactivatedTimeSlots) {
        state.deactivatedTimeSlots = deactivatedTimeSlots
    }
}

export default {
    state,
    getters,
    actions,
    mutations
}