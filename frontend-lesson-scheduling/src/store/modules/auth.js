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
        await commit('loginUser', User.email)
        await commit('setAdmin', isAdmin.data)
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
        console.log(activeUsers.data)
        await commit('setActiveUsers', activeUsers.data)
    },

    async GetDeactivatedUsers({
        commit
    }) {
        let deactivatedUsers = await axios.get('http://localhost:8080/ServletController?action=list-deactivated-users')
        await commit('setDeactivatedUsers', deactivatedUsers.data)
    },

    async GetActiveTeachers({
        commit
    }) {
        let activeTeachers = await axios.get('http://localhost:8080/ServletController?action=list-active-teachers')
        await commit('setActiveTeachers', activeTeachers.data)
    },

    async GetDeactivatedTeachers({
        commit
    }) {
        let deactivatedTeachers = await axios.get('http://localhost:8080/ServletController?action=list-deactivated-teachers')
        await commit('setDeactivatedTeachers', deactivatedTeachers.data)
    },

    async GetTeacherAvailability({
        commit
    }, Teacher) {
        let teacherAvailabilities = await axios.get('http://localhost:8080/ServletController?action=list-teachers-availability', Teacher)
        await commit('setTeacherAvailabilities', teacherAvailabilities.data)
    },

    async GetActiveCourses({
        commit
    }) {
        let activeCourses = await axios.get('http://localhost:8080/ServletController?action=list-active-courses')
        await commit('setActiveCourses', activeCourses.data)
    },

    async GetDeactivatedCourses({
        commit
    }) {
        let deactivatedCourses = await axios.get('http://localhost:8080/ServletController?action=list-deactivated-courses')
        await commit('setDeactivatedCourses', deactivatedCourses.data)
    },

    async GetTeacherCourses({
        commit
    }) {
        let teacherCourses = await axios.get('http://localhost:8080/ServletController?action=list-teacher-courses')
        await commit('setTeacherCourses', teacherCourses.data)
    },

    async GetAllBookings({
        commit
    }) {
        let allBookings = await axios.get('http://localhost:8080/ServletController?action=list-all-bookings')
        console.log(allBookings.data)
        await commit('setAllBookings', allBookings.data)
    },

    async GetAllActiveBookings({
        commit
    }) {
        let allActiveBookings = await axios.get('http://localhost:8080/ServletController?action=list-all-active-bookings')
        await commit('setAllActiveBookings', allActiveBookings.data)
    },

    async GetAllCompletedBookings({
        commit
    }) {
        let allCompletedBookings = await axios.get('http://localhost:8080/ServletController?action=list-all-completed-bookings')
        await commit('setAllCompletedBookings', allCompletedBookings.data)
    },

    async GetAllDeletedBookings({
        commit
    }) {
        let allDeletedBookings = await axios.get('http://localhost:8080/ServletController?action=list-all-deleted-bookings')
        await commit('setAllDeletedBookings', allDeletedBookings.data)
    },

    async GetPersonalBooking({
        commit
    }, User) {
        let personalBookings = await axios.get('http://localhost:8080/ServletController?action=list-personal-bookings', User)
        await commit('setPersonalBookings', personalBookings.data)
    },

    async GetPersonalActiveBooking({
        commit
    }, User) {
        let personalActiveBookings = await axios.get('http://localhost:8080/ServletController?action=list-personal-active-bookings', User)
        await commit('setPersonalActiveBookings', personalActiveBookings.data)
    },

    async GetPersonalCompletedBooking({
        commit
    }, User) {
        let personalCompletedBookings = await axios.get('http://localhost:8080/ServletController?action=list-personal-completed-bookings', User)
        await commit('setPersonalCompletedBookings', personalCompletedBookings.data)
    },

    async GetPersonalDeletedBooking({
        commit
    }, User) {
        let personalDeletedBookings = await axios.get('http://localhost:8080/ServletController?action=list-personal-deleted-bookings', User)
        await commit('setPersonalDeletedBookings', personalDeletedBookings.data)
    },

    async GetTeacherByCourse({
        commit
    }, Course) {
        let teacherByCourse = await axios.get('http://localhost:8080/ServletController?action=list-teacher-by-course', Course)
        await commit('setTeacherByCourse', teacherByCourse.data)
    },

    async GetActiveTimeSlots({
        commit
    }) {
        let activeTimeSlots = await axios.get('http://localhost:8080/ServletController?action=list-active-time-slots')
        await commit('setActiveTimeSlots', activeTimeSlots.data)
    },

    async GetDeactivatedTimeSlots({
        commit
    }) {
        let deactivatedTimeSlots = await axios.get('http://localhost:8080/ServletController?action=list-deactivated-time-slots')
        await commit('setDeactivatedTimeSlots', deactivatedTimeSlots.data)
    }
}

const mutations = {
    loginUser(state, email) {
        state.user = email
    },

    setAdmin(state, isAdmin) {
        state.isAdmin = isAdmin
    },

    logoutUser(state) {
        state.user = null
    },

    setActiveUsers(state, activeUsers) {
        state.activeUsers = activeUsers
    },

    setDeactivatedUsers(state, deactivatedUsers) {
        state.deactivatedUsers = deactivatedUsers
    },

    setActiveTeachers(state, activeTeachers) {
        state.activeTeachers = activeTeachers
    },

    setDeactivatedTeachers(state, deactivatedTeachers) {
        state.deactivatedTeachers = deactivatedTeachers
    },

    setTeacherAvailabilities(state, teacherAvailabilities) {
        state.teacherAvailabilities = teacherAvailabilities
    },

    setActiveCourses(state, activeCourses) {
        state.activeCourses = activeCourses
    },

    setDeactivatedCourses(state, deactivatedCourses) {
        state.deactivatedCourses = deactivatedCourses
    },

    setTeacherCourses(state, teacherCourses) {
        state.teacherCourses = teacherCourses
    },

    setAllBookings(state, allBookings) {
        state.allBookings = allBookings
    },

    setAllActiveBookings(state, allActiveBookings) {
        state.allActiveBookings = allActiveBookings
    },

    setAllCompletedBookings(state, allCompletedBookings) {
        state.allCompletedBookings = allCompletedBookings
    },

    setAllDeletedBookings(state, allDeletedBookings) {
        state.allDeletedBookings = allDeletedBookings
    },

    setPersonalBookings(state, personalBookings) {
        state.personalBookings = personalBookings
    },

    setPersonalActiveBookings(state, personalActiveBookings) {
        state.personalActiveBookings = personalActiveBookings
    },

    setPersonalCompletedBookings(state, personalCompletedBookings) {
        state.personalCompletedBookings = personalCompletedBookings
    },

    setPersonalDeletedBookings(state, personalDeletedBookings) {
        state.personalDeletedBookings = personalDeletedBookings
    },

    setTeacherByCourse(state, teacherByCourse) {
        state.teacherByCourse = teacherByCourse
    },

    setActiveTimeSlots(state, activeTimeSlots) {
        state.activeTimeSlots = activeTimeSlots
    },

    setDeactivatedTimeSlots(state, deactivatedTimeSlots) {
        state.deactivatedTimeSlots = deactivatedTimeSlots
    }
}

export default {
    state,
    getters,
    actions,
    mutations
}