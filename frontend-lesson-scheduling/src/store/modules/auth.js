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
    activeTeacherByCourse: null,
    deactivatedTeacherByCourse: null,
    courseByTeacher: null,
    courseNotTaughtByTeacher: null,
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
    StatePersonalActiveBookings: state => state.personalActiveBookings,
    StatePersonalCompletedBookings: state => state.personalCompletedBookings,
    StatePersonalDeletedBookings: state => state.personalDeletedBookings,
    StateActiveTeacherByCourse: state => state.activeTeacherByCourse,
    StateDeactivatedTeacherByCourse: state => state.deactivatedTeacherByCourse,
    StateCourseByTeacher: state => state.courseByTeacher,
    StateCourseNotTaughtByTeacher: state => state.courseNotTaughtByTeacher,
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

    async InsertUser({
        dispatch
    }, newUser) {
        await axios.post('http://localhost:8080/ServletController?action=insert-user', newUser)
        await dispatch("GetActiveUsers")
    },

    async ActivateUser({
        dispatch
    }, User) {
        await axios.post('http://localhost:8080/ServletController?action=activate-user', User)
        await dispatch("GetActiveUsers")
        await dispatch("GetDeactivatedUsers")
    },

    async DeactivateUser({
        dispatch
    }, User) {
        await axios.post('http://localhost:8080/ServletController?action=deactivate-user', User)
        await dispatch("GetActiveUsers")
        await dispatch("GetDeactivatedUsers")
    },

    async InsertTeacher({
        dispatch
    }, newTeacher) {
        await axios.post('http://localhost:8080/ServletController?action=insert-teacher', newTeacher)
        await dispatch("GetActiveTeachers")
    },

    async ActivateTeacher({
        dispatch
    }, Teacher) {
        await axios.post('http://localhost:8080/ServletController?action=activate-teacher', Teacher)
        await dispatch("GetActiveTeachers")
        await dispatch("GetDeactivatedTeachers")
    },

    async DeactivateTeacher({
        dispatch
    }, Teacher) {
        await axios.post('http://localhost:8080/ServletController?action=deactivate-teacher', Teacher)
        await dispatch("GetActiveTeachers")
        await dispatch("GetDeactivatedTeachers")
    },

    async InsertCourse({
        dispatch
    }, newCourse) {
        await axios.post('http://localhost:8080/ServletController?action=insert-course', newCourse)
        await dispatch("GetActiveCourses")
    },

    async ActivateCourse({
        dispatch
    }, Course) {
        await axios.post('http://localhost:8080/ServletController?action=activate-course', Course)
        await dispatch("GetActiveCourses")
        await dispatch("GetDeactivatedCourses")
    },

    async DeactivateCourse({
        dispatch
    }, Course) {
        await axios.post('http://localhost:8080/ServletController?action=deactivate-course', Course)
        await dispatch("GetActiveCourses")
        await dispatch("GetDeactivatedCourses")
    },

    async InsertTimeSlot({
        dispatch
    }, newTimeSlot) {
        await axios.post('http://localhost:8080/ServletController?action=insert-time-slot', newTimeSlot)
        await dispatch("GetActiveTimeSlots")
    },

    async ActivateTimeSlot({
        dispatch
    }, TimeSlot) {
        await axios.post('http://localhost:8080/ServletController?action=activate-time-slot', TimeSlot)
        await dispatch("GetActiveTimeSlots")
        await dispatch("GetDeactivatedTimeSlots")
    },

    async DeactivateTimeSlot({
        dispatch
    }, TimeSlot) {
        await axios.post('http://localhost:8080/ServletController?action=deactivate-time-slot', TimeSlot)
        await dispatch("GetActiveTimeSlots")
        await dispatch("GetDeactivatedTimeSlots")
    },

    async AssignTeaching({
        dispatch
    }, TeacherCourse) {
        const Teacher = {
            teacherEmail: TeacherCourse.teacherEmail
        }
        await axios.post('http://localhost:8080/ServletController?action=assign-teaching', TeacherCourse)
        await dispatch("GetActiveTeachers")
        await dispatch("GetCourseNotTaughtByTeacher", Teacher)
    },

    async ActivateTeaching({
        dispatch
    }, TeacherCourse) {
        const Course = {
            title: TeacherCourse.title
        }
        await axios.post('http://localhost:8080/ServletController?action=activate-teaching', TeacherCourse)
        await dispatch('GetDeactivatedTeacherByCourse', Course)
        await dispatch('GetActiveTeacherByCourse', Course)
    },

    async DeactivateTeaching({
        dispatch
    }, TeacherCourse) {
        const Course = {
            title: TeacherCourse.title
        }
        await axios.post('http://localhost:8080/ServletController?action=deactivate-teaching', TeacherCourse)
        await dispatch('GetActiveTeacherByCourse', Course)
        await dispatch('GetDeactivatedTeacherByCourse', Course)
    },

    async InsertBooking({
        dispatch
    }, Booking) {
        await axios.post('http://localhost:8080/ServletController?action=insert-booking', Booking)
        await dispatch("GetAllActiveBookings")
    },

    // TODO: Sistemare le chiamate alla lista delle prenotazioni
    async DeleteBooking({
        dispatch
    }, Booking) {
        await axios.post('http://localhost:8080/ServletController?action=delete-booking', Booking)
        await dispatch("GetAllActiveBookings")
        await dispatch("GetAllCompletedBookings")
        await dispatch("GetAllDeletedBookings")
        await dispatch("GetPersonalActiveBooking")
        await dispatch("GetPersonalCompletedBooking")
        await dispatch("GetPersonalDeletedBooking")
    },

    async CompleteBooking({
        dispatch
    }, Booking) {
        await axios.post('http://localhost:8080/ServletController?action=complete-booking', Booking)
        await dispatch("GetAllActiveBookings")
        await dispatch("GetAllCompletedBookings")
        await dispatch("GetAllDeletedBookings")
        await dispatch("GetPersonalActiveBooking")
        await dispatch("GetPersonalCompletedBooking")
        await dispatch("GetPersonalDeletedBooking")
    },

    async GetActiveTeacherByCourse({
        commit
    }, Course) {
        let activeTeacherByCourse = await axios.post('http://localhost:8080/ServletController?action=list-active-teacher-by-course', Course)
        await commit('setActiveTeacherByCourse', activeTeacherByCourse.data)
    },

    async GetDeactivatedTeacherByCourse({
        commit
    }, Course) {
        let deactivatedTeacherByCourse = await axios.post('http://localhost:8080/ServletController?action=list-deactivated-teacher-by-course', Course)
        await commit('setDeactivatedTeacherByCourse', deactivatedTeacherByCourse.data)
    },

    async GetCourseByTeacher({
        commit
    }, Teacher) {
        let courseByTeacher = await axios.post("http://localhost:8080/ServletController?action=list-course-by-teacher", Teacher);
        await commit("setCourseByTeacher", courseByTeacher.data)
    },

    async GetCourseNotTaughtByTeacher({
        commit
    }, Teacher) {
        let courseNotTaughtByTeacher = await axios.post("http://localhost:8080/ServletController?action=list-course-not-taught-by-teacher", Teacher);
        await commit("setCourseNotTaughtByTeacher", courseNotTaughtByTeacher.data)
    },

    async GetTeacherAvailability({
        commit
    }, Teacher) {
        let teacherAvailabilities = await axios.post('http://localhost:8080/ServletController?action=list-teacher-availability', Teacher)
        await commit('setTeacherAvailabilities', teacherAvailabilities.data)
    },

    // GET METHODS
    async GetActiveUsers({
        commit
    }) {
        let activeUsers = await axios.get('http://localhost:8080/ServletController?action=list-active-users')
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

    setActiveTeacherByCourse(state, activeTeacherByCourse) {
        state.activeTeacherByCourse = activeTeacherByCourse
    },

    setDeactivatedTeacherByCourse(state, deactivatedTeacherByCourse) {
        state.deactivatedTeacherByCourse = deactivatedTeacherByCourse
    },

    setCourseByTeacher(state, courseByTeacher) {
        state.courseByTeacher = courseByTeacher
    },

    setCourseNotTaughtByTeacher(state, courseNotTaughtByTeacher) {
        state.courseNotTaughtByTeacher = courseNotTaughtByTeacher
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